package com.aura8.general_backend.clean_arch.infrastructure.dto.job;

public record PatchJobResponse(
        Integer id,
        String name,
        String description,
        Integer expectedDurationMinutes,
        Double price
) {
}
