package com.aura8.general_backend.clean_arch.infrastructure.dto.message;

import com.aura8.general_backend.clean_arch.core.domain.attribute.Email;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ChangePasswordRequest(
        @NotNull
        @Schema(description = "Email do usuário", example = "aura@gmail.com")
        Email email
) {
}
