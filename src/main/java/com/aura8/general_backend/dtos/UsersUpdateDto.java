package com.aura8.general_backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class UsersUpdateDto {
    @NotNull
    private Integer id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotNull
    private Integer roleId;
    @NotNull
    private Boolean deleted;
    private LocalDateTime modifiedAt = LocalDateTime.now();

    public @NotNull Integer getId() {
        return id;
    }

    public void setId(@NotNull Integer id) {
        this.id = id;
    }

    public @NotBlank String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank String username) {
        this.username = username;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }

    public @NotNull Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(@NotNull Integer roleId) {
        this.roleId = roleId;
    }

    public @NotNull Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(@NotNull Boolean deleted) {
        this.deleted = deleted;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
