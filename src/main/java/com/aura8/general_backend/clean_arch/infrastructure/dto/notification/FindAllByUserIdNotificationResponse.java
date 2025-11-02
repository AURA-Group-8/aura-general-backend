package com.aura8.general_backend.clean_arch.infrastructure.dto.notification;

public record FindAllByUserIdNotificationResponse(
        Integer id,
        String message,
        Boolean hasButtonToRate,
        Boolean isAnswered,
        Boolean isRead
) {
}
