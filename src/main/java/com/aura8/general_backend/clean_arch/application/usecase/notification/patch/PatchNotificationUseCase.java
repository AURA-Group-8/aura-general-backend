package com.aura8.general_backend.clean_arch.application.usecase.notification.patch;

import com.aura8.general_backend.clean_arch.core.domain.Notification;
import com.aura8.general_backend.clean_arch.core.gateway.NotificationGateway;
import com.aura8.general_backend.clean_arch.infrastructure.mapper.NotificationMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatchNotificationUseCase {

    private final NotificationGateway notificationGateway;

    public PatchNotificationUseCase(NotificationGateway notificationGateway) {
        this.notificationGateway = notificationGateway;
    }

    public Notification patch(Integer notificationId, PatchNotificationCommand command) {
        Optional<Notification> optionalNotification = notificationGateway.findById(notificationId);
        if (optionalNotification.isEmpty()) {
            throw new IllegalArgumentException("Notification de id (%d) não encontrado".formatted(notificationId));
        }
        Notification notification = optionalNotification.get();
        NotificationMapper.merge(notification, command);
        Notification savedNotification = notificationGateway.patch(notification);
        NotificationMapper.toResponse(savedNotification);
        return savedNotification;
    }
}
