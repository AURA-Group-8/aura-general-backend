package com.aura8.general_backend.clean_arch.infraestructure.dto.users;

import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record PatchUsersRequest(
        Integer id,
        String username,
        String email,
        String password,
        String phone,
        @Past LocalDate dateOfBirth,
        String observation,
        Integer roleId
) {
}
