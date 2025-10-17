package com.aura8.general_backend.clean_arch.infraestructure.dto.users;

import java.time.LocalDate;

public record PatchUsersResponse (
        Integer id,
        String username,
        String email,
        String phone,
        LocalDate dateOfBirth,
        String observation,
        Integer roleId
) {
}
