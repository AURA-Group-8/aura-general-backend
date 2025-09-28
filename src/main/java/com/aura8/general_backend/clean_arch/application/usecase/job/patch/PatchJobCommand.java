package com.aura8.general_backend.clean_arch.application.usecase.job.patch;

public record PatchJobCommand(
        Integer id,
        String name,
        String description,
        Integer expectedDurationMinutes,
        Double price
) {
}
