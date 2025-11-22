package com.aura8.general_backend.clean_arch.infrastructure.dto.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record CreateUsersRequest (
    @NotNull String username,
    @Email String email,
    @NotNull String password,
    @NotNull String phone,
    @Past LocalDate dateOfBirth
) {
}
