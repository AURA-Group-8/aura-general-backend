package com.aura8.general_backend.clean_arch.infrastructure.dto.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record CreateScheduleRequest(
        @NotNull
        @Schema(description = "Id do usuario", example = "1")
        Integer userId,

        @NotNull
        @NotEmpty
        @Schema(description = "Ids dos serviços", example = "[1, 2]")
        List<Integer> jobsIds,

        @NotNull
        @Schema(description = "Horario e dia do agendamento", example = "2025-01-01T10:00:00")
        LocalDateTime startDatetime
) {
}
