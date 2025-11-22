package com.aura8.general_backend.clean_arch.infrastructure.integration.rabbitmq;

public record WhatsappMessage(
        String phone,
        String subject,
        String message
) {
}
