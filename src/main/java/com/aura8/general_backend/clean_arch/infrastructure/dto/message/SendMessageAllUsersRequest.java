package com.aura8.general_backend.clean_arch.infrastructure.dto.message;

import jakarta.validation.constraints.NotBlank;

public record SendMessageAllUsersRequest(
        @NotBlank
        String assunto,
        @NotBlank
        String mensagem
) {
}
