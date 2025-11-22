package com.aura8.general_backend.clean_arch.infrastructure.dto.users;

import com.aura8.general_backend.clean_arch.core.domain.Role;

import java.time.LocalDate;

public record CreateUsersResponse (
        Integer id,
        String username,
        String email,
        String phone,
        LocalDate dateOfBirth,
        Role role,
        String observation
) {
}
