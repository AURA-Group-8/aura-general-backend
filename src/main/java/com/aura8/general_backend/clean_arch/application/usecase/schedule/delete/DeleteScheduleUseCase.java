package com.aura8.general_backend.clean_arch.application.usecase.schedule.delete;

import com.aura8.general_backend.clean_arch.application.exception.ElementNotFoundException;
import com.aura8.general_backend.clean_arch.core.domain.Notification;
import com.aura8.general_backend.clean_arch.core.domain.Schedule;
import com.aura8.general_backend.clean_arch.core.domain.Users;
import com.aura8.general_backend.clean_arch.core.domain.enums.NotificationType;
import com.aura8.general_backend.clean_arch.core.gateway.NotificationGateway;
import com.aura8.general_backend.clean_arch.core.gateway.ScheduleGateway;
import com.aura8.general_backend.clean_arch.core.gateway.UsersGateway;
import org.springframework.stereotype.Service;

@Service
public class DeleteScheduleUseCase {
    private final ScheduleGateway scheduleGateway;
    private final UsersGateway usersGateway;
    private final NotificationGateway notificationGateway;

    public DeleteScheduleUseCase(ScheduleGateway scheduleGateway, UsersGateway usersGateway, NotificationGateway notificationGateway) {
        this.scheduleGateway = scheduleGateway;
        this.usersGateway = usersGateway;
        this.notificationGateway = notificationGateway;
    }

    public void cancel(Integer id, Integer roleId, String motivo) {
        Schedule schedule = scheduleGateway.findById(id);
        if (schedule == null) {
            throw new ElementNotFoundException("Agendamento de id: %d não encontrado".formatted(id));
        }
        scheduleGateway.deleteById(id);
        Integer adminId = 1;
        Integer adminRoleId = 1;
        Users receiver = schedule.getUsers();
        if(roleId != adminRoleId) receiver = usersGateway.findById(adminId).get();
        NotificationType notificationType = NotificationType.SCHEDULE_CANCELED;
        notificationType.setMessage(motivo);
        Notification notification = new Notification(
                receiver,
                schedule,
                notificationType
        );
        notificationGateway.create(notification);
    }
}

