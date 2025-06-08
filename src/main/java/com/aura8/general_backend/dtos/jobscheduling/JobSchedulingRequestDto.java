package com.aura8.general_backend.dtos.jobscheduling;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public class JobSchedulingRequestDto {
    @NotNull
    @Schema(description = "Id do usuario", example = "1")
    private Integer userId;
    @NotNull
    @Schema(description = "Ids dos serviços", example = "[1, 2]")
    private List<Integer> jobsIds;
    @NotNull
    @Schema(description = "Horario e dia do agendamento", example = "2025-01-01T10:00:00")
    private LocalDateTime startDatetime;
    @Schema(description = "Role do usuário que está criando o agendamento", example = "1")
    private Integer roleId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public @NotNull LocalDateTime getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(@NotNull LocalDateTime startDatetime) {
        this.startDatetime = startDatetime;
    }

    public @NotNull Integer getUserId() {
        return userId;
    }

    public void setUserId(@NotNull Integer userId) {
        this.userId = userId;
    }

    public @NotNull List<Integer> getJobsIds() {
        return jobsIds;
    }

    public void setJobsIds(@NotNull List<Integer> jobsIds) {
        this.jobsIds = jobsIds;
    }
}
