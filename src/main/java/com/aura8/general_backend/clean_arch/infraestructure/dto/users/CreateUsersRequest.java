package com.aura8.general_backend.clean_arch.infraestructure.dto.users;

import com.aura8.general_backend.clean_arch.core.domain.attribute.Password;
import com.aura8.general_backend.clean_arch.core.domain.attribute.Phone;
import com.aura8.general_backend.clean_arch.core.domain.attribute.Username;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record CreateUsersRequest (
    @NotNull String username,
    @Email String email,
    @NotNull String password,
    @NotNull String phone,
    @Past LocalDate dateOfBirth,
    @NotNull Integer roleId
) {
}
