package com.aura8.general_backend.dtos.message;

import io.swagger.v3.oas.annotations.media.Schema;

public class ChangePasswordResponseDto {
    @Schema(description = "Token gerado", example = "12345")
    private String token;

    public ChangePasswordResponseDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
