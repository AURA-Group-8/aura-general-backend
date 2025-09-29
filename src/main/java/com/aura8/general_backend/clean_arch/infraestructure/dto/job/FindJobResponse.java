package com.aura8.general_backend.clean_arch.infraestructure.dto.job;

public record FindJobResponse(
        Integer id,
        String name,
        String description,
        Integer expectedDurationMinutes,
        Double price
) {
}
