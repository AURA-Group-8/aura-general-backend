package com.aura8.general_backend.dtos.message;

import io.swagger.v3.oas.annotations.media.Schema;

public class ChangePasswordResponseDto {
    @Schema(description = "Token gerado", example = "12345")
    private String token;

    @Schema(description = "Id do usuario", example = "2")
    private Integer userId;

    public ChangePasswordResponseDto(String token, Integer userId) {
        this.token = token;
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
