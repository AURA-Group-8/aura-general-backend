package com.aura8.general_backend.service;

import com.aura8.general_backend.dtos.finance.MonthDataDto;
import com.aura8.general_backend.dtos.jobscheduling.AvailableDayDto;
import com.aura8.general_backend.dtos.jobscheduling.SchedulingCardResponseDto;
import com.aura8.general_backend.dtos.jobscheduling.SchedulingPatchRequestDto;
import com.aura8.general_backend.dtos.schedulingsettings.SchedulingSettingsListEnumDto;
import com.aura8.general_backend.dtos.schedulingsettings.SchedulingSettingsMapper;
import com.aura8.general_backend.entities.Scheduling;
import com.aura8.general_backend.entities.SchedulingSettings;
import com.aura8.general_backend.entities.Users;
import com.aura8.general_backend.enums.DayOfWeekEnum;
import com.aura8.general_backend.enums.PaymentStatus;
import com.aura8.general_backend.enums.SchedulingStatus;
import com.aura8.general_backend.enums.UpdateTypeEnum;
import com.aura8.general_backend.event.SchedulingCreateEvent;
import com.aura8.general_backend.event.SchedulingDeletedEvent;
import com.aura8.general_backend.event.SchedulingUpdatedEvent;
import com.aura8.general_backend.exception.ConflictException;
import com.aura8.general_backend.exception.ElementNotFoundException;
import com.aura8.general_backend.repository.SchedulingRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SchedulingService {

    private final ApplicationEventPublisher publisher;
    private final SchedulingRepository schedulingRepository;
    private final UsersService usersService;
    private final SchedulingSettingsService schedulingSettingsService;
    private final RoleService roleService;
    private final MessageService messageService;

    public SchedulingService(ApplicationEventPublisher publisher, SchedulingRepository schedulingRepository, UsersService usersService, SchedulingSettingsService schedulingSettingsService, RoleService roleService, MessageService messageService) {
        this.publisher = publisher;
        this.schedulingRepository = schedulingRepository;
        this.usersService = usersService;
        this.schedulingSettingsService = schedulingSettingsService;
        this.roleService = roleService;
        this.messageService = messageService;
    }

    public Scheduling findById(Integer id) {
        return schedulingRepository.findById(id).orElseThrow(
                () -> (new ElementNotFoundException("Scheduling de ID: %d não foi encontrado".formatted(id)))
        );
    }

    public Scheduling create(LocalDateTime startDateTime, LocalDateTime endDateTime, Double totalPrice, Integer userId, Integer roleId) {
        Scheduling scheduling = new Scheduling();
        Users user = usersService.getUserById(userId);
        scheduling.setStartDatetime(startDateTime);
        scheduling.setEndDatetime(endDateTime);
        scheduling.setTotalPrice(totalPrice);
        scheduling.setUsers(user);
        scheduling.setPaymentStatus(PaymentStatus.PENDENTE);
        scheduling.setStatus(SchedulingStatus.PENDENTE);

//      Validar de dia está disponível
        // 1. Pegar configurações
        SchedulingSettings schedulingSettings = schedulingSettingsService.getSchedulingSettings();
        SchedulingSettingsListEnumDto settings = SchedulingSettingsMapper.toListEnumDto(schedulingSettings);
        // 2. validar
        if(!settings.getDaysOfWeek().contains(startDateTime.toLocalDate().getDayOfWeek())){
            throw new ConflictException("O dia %s não está disponível".formatted(startDateTime.toLocalDate().toString()));
        }

//      Validar se horario está disponivel
        // 1. Pegar agendamentos do dia
        List<Scheduling> schedulingsOfTheDay = schedulingRepository.findByStartDatetimeBetweenAndIsCanceledFalse(
                startDateTime.toLocalDate().atStartOfDay(),
                endDateTime
        );
        // 2. transformar dados
        LocalTime startTime = startDateTime.toLocalTime();
        LocalTime endTime = endDateTime.toLocalTime();

        // 3. Verificar se o horario é valido
        for(Scheduling schedlingToValidate : schedulingsOfTheDay) {
            LocalTime start = schedlingToValidate.getStartDatetime().toLocalTime();
            LocalTime end = schedlingToValidate.getEndDatetime().toLocalTime();

            if(
                (startTime.isAfter(start) && startTime.isBefore(end)) ||
                (endTime.isAfter(start) && endTime.isBefore(end)) ||
                (startTime.equals(start) || endTime.equals(end))
            ){
                throw new ConflictException("Horário das %s às %s não está disponível para o dia %s".formatted(
                        startTime.toString(), endTime.toString(), startDateTime.toLocalDate().toString()
                ));
            }
        }
        if(
            (startTime.isBefore(settings.getWorkStart()) || endTime.isAfter(settings.getWorkEnd()))
        ){
            throw new ConflictException(
                    "Horário das %s às %s não está disponível. ".formatted(
                            startTime.toString(),
                            endTime.toString()
                    ) +
                    "Horário de funcionamento apenas das %s às %s e das %s às %s".formatted(
                            settings.getWorkStart(), settings.getBreakStart(),
                            settings.getBreakEnd(), settings.getWorkEnd()
                    )
            );
        }

        Boolean isAdminScheduling = roleService.getRoleById(roleId).getName().equals("ADMIN");
        Scheduling savedScheduling = schedulingRepository.save(scheduling);
        publisher.publishEvent(new SchedulingCreateEvent(this, isAdminScheduling, user, savedScheduling));
        return savedScheduling;
    }

    public Page<Scheduling> findAll(Pageable pageable) {
        return schedulingRepository.findAll(pageable);
    }

    public List<AvailableDayDto> getAvailableTimes(Integer durationInMinutes, LocalDate firstDayOfWeek) {
        LocalDate today = firstDayOfWeek;
        int dayOfWeeek = today.getDayOfWeek().getValue();
        if (dayOfWeeek == 7) {
            dayOfWeeek = 0; // Ajusta para que domingo seja o primeiro dia da semana
        }
        LocalDate startOfWeek = today.minusDays(dayOfWeeek);
        int daysToNextWeek = 6 - dayOfWeeek;
        List<Scheduling> schedulings = schedulingRepository.findByStartDatetimeBetweenAndIsCanceledFalse(startOfWeek.atStartOfDay(), today.plusDays(daysToNextWeek).atStartOfDay());
        SchedulingSettings schedulingSettings = schedulingSettingsService.getSchedulingSettings();
        SchedulingSettingsListEnumDto settings = SchedulingSettingsMapper.toListEnumDto(schedulingSettings);

        List<LocalDate> dates = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            dates.add(startOfWeek.plusDays(i));
        }
        List<AvailableDayDto> availableDays = new ArrayList<>();
        for (LocalDate date : dates) {
            AvailableDayDto availableDay = new AvailableDayDto();
            availableDay.setDate(date);
            availableDay.setAvailable(true); // Inicialmente assume que o dia está disponível
            availableDay.setAvailableTimes(new ArrayList<>());
            availableDay.setWeekDay(DayOfWeekEnum.getNameByNumber(date.getDayOfWeek().getValue()));

            List<Scheduling> dailySchedulings = schedulings.stream()
                    .filter(scheduling -> scheduling.getStartDatetime().toLocalDate().equals(date))
                    .toList();

            List<DayOfWeek> avaliablesWeekDays = settings.getDaysOfWeek();
            // Verifica se o dia está disponível nas configurações
            if (!avaliablesWeekDays.contains(date.getDayOfWeek())) {
                availableDay.setAvailable(false); // Dia não disponível
            } else {
                // Adiciona os horários disponíveis baseados nas configurações
                LocalTime startTime = settings.getWorkStart();
                LocalTime endTime = settings.getWorkEnd();
                LocalTime breakStart = settings.getBreakStart();
                LocalTime breakEnd = settings.getBreakEnd();

                // Adiciona os horários antes do intervalo de almoço
                while (startTime.isBefore(breakStart)) {
                    availableDay.getAvailableTimes().add(startTime);
                    startTime = startTime.plusMinutes(30);
                }

                // Adiciona os horários após o intervalo de almoço
                startTime = breakEnd;
                while (startTime.isBefore(endTime)) {
                    availableDay.getAvailableTimes().add(startTime);
                    startTime = startTime.plusMinutes(30);
                }
            }

            availableDay.getAvailableTimes().removeIf(time -> (time.plusMinutes(durationInMinutes).isAfter(settings.getWorkEnd())));

            // Remove os horários que já estão ocupados
            for (Scheduling scheduling : dailySchedulings) {
                LocalTime start = scheduling.getStartDatetime().toLocalTime();
                LocalTime end = scheduling.getEndDatetime().toLocalTime();

                // Remove os horários ocupados
                availableDay.getAvailableTimes().removeIf(time ->
                        (time.isAfter(start) && time.isBefore(end)) ||
                                (time.plusMinutes(durationInMinutes).isAfter(start) && time.plusMinutes(durationInMinutes).isBefore(end)) ||
                                (time.equals(start) || time.plusMinutes(durationInMinutes).equals(end))
                );

                // Se não houver horários disponíveis, marca o dia como indisponível
                if (availableDay.getAvailableTimes().isEmpty()) {
                    availableDay.setAvailable(false);
                }
            }
            availableDays.add(availableDay);
        }
        return availableDays;
    }

    public List<SchedulingCardResponseDto> getCardInfos() {
        List<SchedulingCardResponseDto> cards = new ArrayList<>();
        List<Scheduling> schedulings = schedulingRepository.findAll();
        schedulings.forEach(scheduling -> {
            SchedulingCardResponseDto card = new SchedulingCardResponseDto();
            card.setIdScheduling(scheduling.getId());
            card.setUserName(scheduling.getUsers().getUsername());
            card.setStartDatetime(scheduling.getStartDatetime());
            card.setEndDatetime(scheduling.getEndDatetime());
            card.setTotalPrice(scheduling.getTotalPrice());
            card.setPaymentStatus(scheduling.getPaymentStatus().getDescription());
            card.setStatus(scheduling.getStatus().getStatus());
            cards.add(card);
        });
        return cards;
    }

    public Scheduling update(Integer id, SchedulingPatchRequestDto schedulingPatchRequestDto) {
        Boolean statusHasModified = false;
        Boolean feedbackHasModified = false;

        Scheduling scheduling = findById(id);
        schedulingPatchRequestDto.setId(id);
        if (schedulingPatchRequestDto.getFeedback() != null) {
            scheduling.setFeedback(schedulingPatchRequestDto.getFeedback());
            feedbackHasModified = true;
        }
        if (schedulingPatchRequestDto.getStatus() != null) {
            try {
                scheduling.setStatus(SchedulingStatus.valueOf(schedulingPatchRequestDto.getStatus()));
                if(SchedulingStatus.valueOf(schedulingPatchRequestDto.getStatus()).equals(SchedulingStatus.FEITO)) statusHasModified = true;
            } catch (IllegalArgumentException e) {
                throw new ElementNotFoundException("Status de agendamento inválido: %s. Tente: [FEITO, PENDENTE, CANCELADO]".formatted(schedulingPatchRequestDto.getStatus()));
            }
        }
        if (schedulingPatchRequestDto.getPaymentStatus() != null) {
            try {
                scheduling.setPaymentStatus(PaymentStatus.valueOf(schedulingPatchRequestDto.getPaymentStatus()));
            } catch (IllegalArgumentException e) {
                throw new ElementNotFoundException("Status de pagamento inválido: %s. Tente: [PAGO, PENDENTE]".formatted(schedulingPatchRequestDto.getPaymentStatus()));
            }
        }
        Scheduling savedScheduling = schedulingRepository.save(scheduling);
        Integer agendamentos = schedulingRepository.countByUsersIdAndStatusAndIsCanceledFalse(savedScheduling.getUsers().getId(), SchedulingStatus.FEITO);
        if(statusHasModified && agendamentos == 1) publisher.publishEvent(new SchedulingUpdatedEvent(this, savedScheduling, savedScheduling.getUsers(), true, UpdateTypeEnum.STATUS));
        if(feedbackHasModified) publisher.publishEvent(new SchedulingUpdatedEvent(this, savedScheduling, savedScheduling.getUsers(), false, UpdateTypeEnum.FEEDBACK));
        return savedScheduling;
    }

    public MonthDataDto generateFinanceMonthData(LocalDate month) {
        LocalDateTime startOfMonth = month.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = month.withDayOfMonth(month.lengthOfMonth()).atTime(23, 59, 59);
        Double totalFaturadoMes = schedulingRepository.getTotalFaturadoMes(startOfMonth, endOfMonth);
        Integer totalAtendimentosMes = schedulingRepository.getTotalAtendimentosMes(startOfMonth, endOfMonth);
        Integer totalAtendimentosCanceladosMes = schedulingRepository.getTotalAtendimentosCanceladosMes(startOfMonth, endOfMonth);
        if (totalFaturadoMes == null) {
            totalFaturadoMes = 0.0;
        }
        return new MonthDataDto(totalFaturadoMes, totalAtendimentosMes, totalAtendimentosCanceladosMes);
    }

    public List<String> getTopClientes() {
        List<String> topServicos = schedulingRepository.getTopClientes();
        topServicos = topServicos.stream()
                .map(servico -> servico.split(",")[0])
                .toList();
        System.out.println("Top Serviços: " + topServicos);
        return topServicos;
    }

    public List<Integer> getAtendimentosDiaDaSemanaNoMes(LocalDate month) {
        LocalDateTime startOfMonth = month.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = month.withDayOfMonth(month.lengthOfMonth()).atTime(23, 59, 59);
        List<Scheduling> schedulings = schedulingRepository.findByStartDatetimeBetweenAndIsCanceledFalse(startOfMonth, endOfMonth);
        List<Integer> atendimentosDiaDaSemana = new ArrayList<>(7);
        for (int i = 0; i < 7; i++) {
            atendimentosDiaDaSemana.add(0);
        }
        schedulings.forEach(
                scheduling -> {
                    int index_dia_da_semana = scheduling.getStartDatetime().getDayOfWeek().getValue() - 1;
                    int oldValue = atendimentosDiaDaSemana.get(index_dia_da_semana);
                    atendimentosDiaDaSemana.set(index_dia_da_semana, oldValue+1);
                }
        );
        System.out.println("Atendimentos por dia da semana: " + atendimentosDiaDaSemana);
        return atendimentosDiaDaSemana;
    }


    public LocalDate getDateFirstScheduling() {
        return schedulingRepository.findFirstDateScheduling()
                .orElseThrow(() -> new ElementNotFoundException("Nenhum agendamento encontrado"))
                .toLocalDate();
    }

    public void delete(Integer idScheduling, String message, Integer roleId) {
        Scheduling scheduling = findById(idScheduling);
        scheduling.setCanceled(true);
        scheduling.setCanceledAt(LocalDateTime.now());
        scheduling.setStatus(SchedulingStatus.CANCELADO);
        Users user = scheduling.getUsers();
        Boolean isAdmin = false;
        int adminId = 1;
        if(roleId == adminId) isAdmin = true;
        publisher.publishEvent(new SchedulingDeletedEvent(this, scheduling, message, user, isAdmin));
        schedulingRepository.save(scheduling);
    }

    @Scheduled(cron = "* */30 10 * * *")
    public void sendMensage(){
        LocalDateTime now = LocalDateTime.now();
        System.out.println("%s: Iniciando processo agendado de envio de mensagens".formatted(now.toString()));
        LocalDateTime amanha = now.toLocalDate().plusDays(1).atStartOfDay();
        List<Scheduling> agendamentosAmanha = schedulingRepository.findByStartDatetimeBetweenAndIsCanceledFalse(amanha, amanha.plusHours(24));
        for (Scheduling scheduling : agendamentosAmanha) {
            messageService.sendMensagemValidUserPresence(scheduling.getUsers().getPhone());
        }
    }
}
