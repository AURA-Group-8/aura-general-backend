package com.aura8.general_backend.clean_arch.application.usecase.users.patch;

import java.time.LocalDate;

public record PatchUsersCommand(
        Integer id,
        String username,
        String email,
        String password,
        String phone,
        LocalDate dateOfBirth,
        String observation,
        Integer roleId
) {
}
