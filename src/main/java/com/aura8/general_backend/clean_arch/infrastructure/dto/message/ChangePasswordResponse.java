package com.aura8.general_backend.clean_arch.infrastructure.dto.message;

import io.swagger.v3.oas.annotations.media.Schema;

public record ChangePasswordResponse(
        @Schema(description = "Token gerado", example = "12345")
        String token,
        @Schema(description = "Id do usuario", example = "2")
        Integer userId
) {
}
