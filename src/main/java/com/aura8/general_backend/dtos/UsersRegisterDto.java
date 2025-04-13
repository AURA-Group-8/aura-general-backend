package com.aura8.general_backend.dtos;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class UsersRegisterDto {

    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String phone;
    @NotNull
    private LocalDateTime dateOfBirth;
    @NotNull
    private Integer roleId;

    public @NotBlank String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank String username) {
        this.username = username;
    }

    public @NotBlank String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank String email) {
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

    public @NotNull LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(@NotNull LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public @NotNull Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(@NotNull Integer roleId) {
        this.roleId = roleId;
    }
}

