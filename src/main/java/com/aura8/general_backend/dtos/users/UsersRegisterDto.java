package com.aura8.general_backend.dtos.users;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class UsersRegisterDto {

    @NotBlank
    @Schema(description = "Nome do usuário", example = "João")
    private String username;
    @NotBlank
    @Email
    @Schema(description = "Email do usuário", example = "aura@gmail.com")
    private String email;
    @NotBlank
    @Schema(description = "Senha do usuário", example = "Th1sPassword!")
    private String password;
    @NotBlank
    @Schema(description = "Telefone do usuário", example = "+55 11 91234-5678")
    private String phone;

    private LocalDateTime dateOfBirth;
    @NotNull
    private Integer roleId;

    public @NotBlank String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank String username) {
        this.username = username;
    }

    public @NotBlank @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @Email String email) {
        this.email = email;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }

    public @NotBlank String getPhone() {
        return phone;
    }

    public void setPhone(@NotBlank String phone) {
        this.phone = phone;
    }

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public @NotNull Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(@NotNull Integer roleId) {
        this.roleId = roleId;
    }
}

