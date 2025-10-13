package com.aura8.general_backend.clean_arch.infraestructure.dto.users;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
