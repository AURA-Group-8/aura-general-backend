package com.aura8.general_backend.clean_arch.infrastructure.persistence.entity.id;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class JobScheduleId implements Serializable {
    private Integer jobId;
    private Integer scheduleId;

    public JobScheduleId() {
    }

    public JobScheduleId(Integer jobId, Integer scheduleId) {
        this.jobId = jobId;
        this.scheduleId = scheduleId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobScheduleId that)) return false;
        return Objects.equals(jobId, that.jobId) && Objects.equals(scheduleId, that.scheduleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId, scheduleId);
    }
}
