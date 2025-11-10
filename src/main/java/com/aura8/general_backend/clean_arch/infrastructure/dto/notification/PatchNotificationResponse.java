package com.aura8.general_backend.clean_arch.infrastructure.dto.notification;

public record PatchNotificationResponse(
    Integer id,
    String message,
    Boolean hasButtonToRate,
    Boolean answered,
    Boolean read
) {
}
