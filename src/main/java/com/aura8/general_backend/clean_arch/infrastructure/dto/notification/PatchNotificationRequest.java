package com.aura8.general_backend.clean_arch.infrastructure.dto.notification;

public record PatchNotificationRequest(
        Integer id,
        Boolean isRead
) {
}
