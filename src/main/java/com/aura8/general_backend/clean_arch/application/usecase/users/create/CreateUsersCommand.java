package com.aura8.general_backend.clean_arch.application.usecase.users.create;

import java.time.LocalDate;

public record CreateUsersCommand(
        String username,
        String email,
        String password,
        String phone,
        LocalDate dateOfBirth
) {
}
