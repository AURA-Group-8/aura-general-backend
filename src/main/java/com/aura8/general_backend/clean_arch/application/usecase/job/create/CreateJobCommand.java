package com.aura8.general_backend.clean_arch.application.usecase.job.create;

public record CreateJobCommand(
        String name,
        String description,
        Integer expectedDurationMinutes,
        Double price
) {
}
