package com.aura8.general_backend.clean_arch.infrastructure.dto.users;

import java.time.LocalDate;

public record FindUsersResponse(
        Integer id,
        String username,
        String email,
        String phone,
        LocalDate dateOfBirth,
        Integer roleId,
        String observation
) {
}
