package com.aura8.general_backend.clean_arch.infraestructure.dto.job;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record PatchJobResponse(
        Integer id,
        String name,
        String description,
        Integer expectedDurationMinutes,
        Double price
) {
}
