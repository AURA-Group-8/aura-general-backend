package com.aura8.general_backend.clean_arch.application.usecase.users.patch;

import com.aura8.general_backend.clean_arch.core.domain.Role;

import java.time.LocalDate;

public record PatchUsersCommand(
        Integer id,
        String username,
        String email,
        String password,
        String phone,
        LocalDate dateOfBirth,
        String observation,
        Integer roleId,
        Role role
) {
}
