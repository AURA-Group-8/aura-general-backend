package com.aura8.general_backend.clean_arch.infrastructure.integration.rabbitmq;

public record EmailMessage(
        String from,
        String to,
        String subject,
        String text
) {
}
