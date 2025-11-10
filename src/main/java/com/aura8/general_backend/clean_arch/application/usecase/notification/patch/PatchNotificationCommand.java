package com.aura8.general_backend.clean_arch.application.usecase.notification.patch;

public record PatchNotificationCommand(
    Boolean read,
    Boolean answered
) {
}
