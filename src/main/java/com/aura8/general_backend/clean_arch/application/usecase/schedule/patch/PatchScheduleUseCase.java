package com.aura8.general_backend.clean_arch.application.usecase.schedule.patch;

import com.aura8.general_backend.clean_arch.application.exception.ElementNotFoundException;
import com.aura8.general_backend.clean_arch.core.domain.Notification;
import com.aura8.general_backend.clean_arch.core.domain.Schedule;
import com.aura8.general_backend.clean_arch.core.domain.Users;
import com.aura8.general_backend.clean_arch.core.domain.enums.NotificationType;
import com.aura8.general_backend.clean_arch.core.domain.enums.ScheduleStatus;
import com.aura8.general_backend.clean_arch.core.gateway.NotificationGateway;
import com.aura8.general_backend.clean_arch.core.gateway.ScheduleGateway;
import com.aura8.general_backend.clean_arch.core.gateway.UsersGateway;
import com.aura8.general_backend.clean_arch.infrastructure.mapper.ScheduleMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatchScheduleUseCase {
    private final ScheduleGateway scheduleGateway;
    private final NotificationGateway notificationGateway;
    private final UsersGateway usersGateway;

    public PatchScheduleUseCase(ScheduleGateway scheduleGateway, NotificationGateway notificationGateway, UsersGateway usersGateway) {
        this.scheduleGateway = scheduleGateway;
        this.notificationGateway = notificationGateway;
        this.usersGateway = usersGateway;
    }

    public Schedule patch(Integer scheduleId, PatchScheduleCommand command) {
        Schedule schedule = scheduleGateway.findById(scheduleId);
        if(schedule == null) {
            throw new ElementNotFoundException("Schedule de id: %d nao encontrado".formatted(scheduleId));
        }
        if(command.status() != null && command.status() == ScheduleStatus.CANCELADO) {
            throw new IllegalArgumentException("Marcar agendamento como cancelado pelo metodo patch não é permitido. Use o metodo Delete.");
        }
        ScheduleMapper.mergePatchCommandInDomain(command, schedule);
        Schedule patch = scheduleGateway.patch(schedule);

        Integer adminId = 1;
        Integer adminRoleId = 1;
        Users sender = usersGateway.findById(adminId).get();
        Users receiver = schedule.getUsers();
        List<Schedule> schedules = scheduleGateway.findByUsersId(schedule.getUsers().getId());
        List<Schedule> completedSchedules = schedules.stream().filter(s -> s.getStatus() == ScheduleStatus.FEITO).toList();

        NotificationType notificationType = null;
        if(command.feedback() != null) {
            Users tempSender = sender;
            sender = receiver;
            receiver = tempSender;
            notificationType = NotificationType.RATE_SERVICE;
        } else if(command.status() != null && command.status() == ScheduleStatus.FEITO && completedSchedules.size() < 2) {
            notificationType = NotificationType.FIRST_SCHEDULE_COMPLETED;
        }
        if (notificationType == null) {
            return patch;
        }
        notificationType.setActor(sender.getUsername().get());
        Notification notification = new Notification(
                receiver,
                patch,
                notificationType
        );
        notificationGateway.create(notification);

        return patch;
    }
}
