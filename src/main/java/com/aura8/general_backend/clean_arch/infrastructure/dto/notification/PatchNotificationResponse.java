package com.aura8.general_backend.clean_arch.infrastructure.dto.notification;

import java.time.LocalDateTime;

public record PatchNotificationResponse(
    Integer id,
    String message,
    Boolean hasButtonToRate,
    Boolean answered,
    Boolean read,
    LocalDateTime createdAt
) {
}
