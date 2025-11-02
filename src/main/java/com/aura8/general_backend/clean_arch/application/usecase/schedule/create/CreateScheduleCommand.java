package com.aura8.general_backend.clean_arch.application.usecase.schedule.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record CreateScheduleCommand(
        Integer userId,
        List<Integer> jobsIds,
        LocalDateTime startDatetime,
        Integer roleId
) {
}
