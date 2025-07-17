package com.aura8.general_backend.dtos.message;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ChangePasswordRequestDto {
    @Email
    @NotBlank
    @Schema(description = "Email do usuário", example = "aura@gmail.com")
    private String email;

    public @Email @NotBlank String getEmail() {
        return email;
    }

    public void setEmail(@Email @NotBlank String email) {
        this.email = email;
    }
}
