package com.aura8.general_backend.clean_arch.infrastructure.mapper;

import com.aura8.general_backend.clean_arch.application.usecase.notification.patch.PatchNotificationCommand;
import com.aura8.general_backend.clean_arch.core.domain.Notification;
import com.aura8.general_backend.clean_arch.infrastructure.dto.notification.FindAllByUserIdNotificationResponse;
import com.aura8.general_backend.clean_arch.infrastructure.dto.notification.PatchNotificationRequest;
import com.aura8.general_backend.clean_arch.infrastructure.dto.notification.PatchNotificationResponse;
import com.aura8.general_backend.clean_arch.infrastructure.persistence.entity.NotificationEntity;

public class NotificationMapper {

    public static Notification toDomain(NotificationEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Notification(
                entity.getId(),
                UsersMapper.toDomain(entity.getUsers()),
                ScheduleMapper.toDomain(entity.getSchedule()),
                entity.getMessage(),
                entity.getHasButtonToRate(),
                entity.getWasAnswered(),
                entity.getWasRead()
        );
    }


    public static PatchNotificationCommand toCommand(PatchNotificationRequest request) {
        if (request == null) {
            return null;
        }
        return new PatchNotificationCommand(
                request.isRead(),
                null
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
        entity.setUsers(UsersMapper.toEntity(domain.getUsers()));
        entity.setSchedule(ScheduleMapper.toEntity(domain.getScheduling()));
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

    public static PatchNotificationResponse toPatchResponse(Notification domain) {
        if (domain == null) {
            return null;
        }
        return new PatchNotificationResponse(
                domain.getId(),
                domain.getMessage(),
                domain.getHasButtonToRate(),
                domain.getAnswered(),
                domain.getRead()
        );
    }

    public static void merge(NotificationEntity target, NotificationEntity source) {
        if (source.getHasButtonToRate() != null) {
            target.setHasButtonToRate(source.getHasButtonToRate());
        }
        if (source.getWasAnswered() != null) {
            target.setWasAnswered(source.getWasAnswered());
        }
        if (source.getWasRead() != null) {
            target.setWasRead(source.getWasRead());
        }
    }

    public static void merge(Notification target, PatchNotificationCommand source) {
        if (source.answered() != null) {
            target.setAnswered(source.answered());
        }
        if (source.read() != null) {
            target.setRead(source.read());
        }
    }
}
