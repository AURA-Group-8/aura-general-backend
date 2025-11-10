package com.aura8.general_backend.clean_arch.infrastructure.dto.notification;

import io.swagger.v3.oas.annotations.media.Schema;

public record PatchNotificationRequest(
        Integer id,
        @Schema(description = "Indica se a notificação foi lida", example = "true")
        Boolean isRead
) {
}
