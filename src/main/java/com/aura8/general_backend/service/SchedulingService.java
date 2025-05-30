package com.aura8.general_backend.service;

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
import com.aura8.general_backend.event.SchedulingCreateEvent;
import com.aura8.general_backend.exception.ElementNotFoundException;
import com.aura8.general_backend.repository.SchedulingRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SchedulingService {

    private final ApplicationEventPublisher publisher;
    private final SchedulingRepository schedulingRepository;
    private final UsersService usersService;
    private final SchedulingSettingsService schedulingSettingsService;

    public SchedulingService(ApplicationEventPublisher publisher, SchedulingRepository schedulingRepository, UsersService usersService, SchedulingSettingsService schedulingSettingsService) {
        this.publisher = publisher;
        this.schedulingRepository = schedulingRepository;
        this.usersService = usersService;
        this.schedulingSettingsService = schedulingSettingsService;
    }

    public Scheduling findById(Integer id) {
        return schedulingRepository.findById(id).orElseThrow(
                () -> (new ElementNotFoundException("Scheduling de ID: %d não foi encontrado".formatted(id)))
        );
    }

    public Scheduling create(Scheduling scheduling, Integer userId) {
        Users user = usersService.getUserById(userId);
        scheduling.setUsers(user);
        scheduling.setPaymentStatus(PaymentStatus.PENDENTE);
        scheduling.setStatus(SchedulingStatus.PENDENTE);
        Boolean isAdminScheduling = user.getRole().getName().equals("ADMIN");
        publisher.publishEvent(new SchedulingCreateEvent(this, isAdminScheduling, user, scheduling));
        return schedulingRepository.save(scheduling);
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
        List<Scheduling> schedulings = schedulingRepository.findByStartDatetimeBetween(startOfWeek.atStartOfDay(), today.plusDays(daysToNextWeek).atStartOfDay());
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
        Scheduling scheduling = findById(id);
        schedulingPatchRequestDto.setId(id);
        if (schedulingPatchRequestDto.getFeedback() != null) {
            scheduling.setFeedback(schedulingPatchRequestDto.getFeedback());
        }
        if (schedulingPatchRequestDto.getStatus() != null) {
            try {
                scheduling.setStatus(SchedulingStatus.valueOf(schedulingPatchRequestDto.getStatus()));
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
        return schedulingRepository.save(scheduling);
    }
}
