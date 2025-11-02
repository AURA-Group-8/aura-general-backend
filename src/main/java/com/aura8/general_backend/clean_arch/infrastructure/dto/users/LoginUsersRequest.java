package com.aura8.general_backend.clean_arch.infrastructure.dto.users;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class LoginUsersRequest {

    @NotBlank
    @Schema(description = "Email do usuário", example = "aura@gmail.com")
    private String email;
    @NotBlank
    @Schema(description = "Senha do usuário", example = "Th1sPassword!")
    private String password;

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }

    public @NotBlank String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank String email) {
        this.email = email;
    }
}
