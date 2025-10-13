package com.aura8.general_backend.clean_arch.infraestructure.mapper;

import com.aura8.general_backend.clean_arch.core.domain.Notification;
import com.aura8.general_backend.clean_arch.infraestructure.dto.notification.FindAllByUserIdNotificationResponse;
import com.aura8.general_backend.clean_arch.infraestructure.persistence.entity.NotificationEntity;

public class NotificationMapper {

    public static Notification toDomain(NotificationEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Notification(
                entity.getId(),
                null,
                null,
                entity.getMessage(),
                entity.getHasButtonToRate(),
                entity.getWasAnswered(),
                entity.getWasRead()
        );
    }

    public static NotificationEntity toEntity(Notification domain) {
        if (domain == null) {
            return null;
        }
        NotificationEntity entity = new NotificationEntity();
        entity.setId(domain.getId());
        entity.setMessage(domain.getMessage());
        entity.setHasButtonToRate(domain.getHasButtonToRate());
        entity.setWasAnswered(domain.getAnswered());
        entity.setWasRead(domain.getRead());
        return entity;
    }

    public static FindAllByUserIdNotificationResponse toResponse(Notification domain) {
        if (domain == null) {
            return null;
        }
        return new FindAllByUserIdNotificationResponse(
                domain.getId(),
                domain.getMessage(),
                domain.getHasButtonToRate(),
                domain.getAnswered(),
                domain.getRead()
        );
    }
}
