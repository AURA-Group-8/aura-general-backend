package com.aura8.general_backend.dtos.jobscheduling;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public class JobSchedulingRequestDto {
    @NotNull
    private Integer userId;
    @NotNull
    private List<Integer> jobsIds;
    @NotNull
    private LocalDateTime startDatetime;

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
