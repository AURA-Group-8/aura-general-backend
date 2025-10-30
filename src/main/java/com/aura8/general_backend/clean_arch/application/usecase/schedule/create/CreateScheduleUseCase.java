package com.aura8.general_backend.clean_arch.application.usecase.schedule.create;

import com.aura8.general_backend.clean_arch.core.domain.*;
import com.aura8.general_backend.clean_arch.core.domain.enums.NotificationType;
import com.aura8.general_backend.clean_arch.core.gateway.*;
import com.aura8.general_backend.exception.ConflictException;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class CreateScheduleUseCase {
    private final ScheduleGateway scheduleGateway;
    private final UsersGateway usersGateway;
    private final JobGateway jobGateway;
    private final ScheduleSettingGateway scheduleSettingGateway;
    private final JobScheduleGateway jobScheduleGateway;
    private final NotificationGateway notificationGateway;

    public CreateScheduleUseCase(ScheduleGateway scheduleGateway, UsersGateway usersGateway, JobGateway jobGateway, ScheduleSettingGateway scheduleSettingGateway, JobScheduleGateway jobScheduleGateway, NotificationGateway notificationGateway) {
        this.scheduleGateway = scheduleGateway;
        this.usersGateway = usersGateway;
        this.jobGateway = jobGateway;
        this.scheduleSettingGateway = scheduleSettingGateway;
        this.jobScheduleGateway = jobScheduleGateway;
        this.notificationGateway = notificationGateway;
    }

    public Schedule create(CreateScheduleCommand command) {
        Optional<Users> optionalUsers = usersGateway.findById(command.userId());
        if (optionalUsers.isEmpty()) {
            throw new IllegalArgumentException("Usuário de id (%d) não encontrado".formatted(command.userId()));
        }
        Users users = optionalUsers.get();
        List<Job> jobs = command.jobsIds().stream().map(jobId -> {
            Optional<Job> optionalJob = jobGateway.findById(jobId);
            if (optionalJob.isEmpty()) {
                throw new IllegalArgumentException("Serviço de id (%d) não encontrado".formatted(jobId));
            }
            return optionalJob.get();
        }).toList();


        //Cria o agendamento falso apenas para pegar dados do contrutor
        Schedule schedule = new Schedule(
                users,
                jobs,
                command.startDatetime()
        );

        int rangeInDaysOfNextSchedules = 7;
        LocalDateTime startRange = schedule.getStartDatetime().truncatedTo(ChronoUnit.DAYS);
        LocalDateTime endRange = startRange.plusDays(rangeInDaysOfNextSchedules);
        List<Schedule> schedulesInRange = scheduleGateway.findByStartDateBetween(startRange, endRange);
        boolean hasConflict = schedulesInRange.stream().anyMatch(schedule::isScheduleColliding);
        if (hasConflict) {
            throw new ConflictException("Conflito de agendamento: já existe um agendamento nesse período. Das %s ás %s de %s".formatted(
                    schedule.getStartDatetime().toLocalTime().toString(),
                    schedule.getEndDatetime().toLocalTime().toString(),
                    schedule.getStartDatetime().toLocalDate().toString()
            ));
        }

        ScheduleSetting scheduleSetting = scheduleSettingGateway.findById(1);
        if (scheduleSetting == null) {
            throw new IllegalArgumentException("Configuração de agenda não encontrada.");
        }
        Boolean timesInWorkTime = scheduleSetting.isScheduleInAvaliableDateTime(schedule);
        if(!timesInWorkTime) {
            throw new ConflictException("Horário de agendamento fora do horário de trabalho.");
        }

        Schedule saved = scheduleGateway.create(schedule);

        jobScheduleGateway.create(saved, jobs);

        Users sender = schedule.getUsers();
        Users receiver = schedule.getUsers();
        Integer adminId = 1;
        Integer adminRoleId = 1;
        if(command.roleId() != adminRoleId) {
            receiver = usersGateway.findById(adminId).get();
        } else {
            sender = usersGateway.findById(adminId).get();
        }
        NotificationType notificationType = NotificationType.SCHEDULE_CONFIRMED;
        notificationType.setActor(sender.getUsername().get());
        Notification notification = new Notification(
                receiver,
                saved,
                notificationType
        );
        notificationGateway.create(notification);
        return saved;
    }
}
