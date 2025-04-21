package com.aura8.general_backend.entities.id;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class JobSchedulingId implements Serializable {
    private Integer jobId;
    private Integer schedulingId;

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getSchedulingId() {
        return schedulingId;
    }

    public void setSchedulingId(Integer schedulingId) {
        this.schedulingId = schedulingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobSchedulingId that)) return false;
        return Objects.equals(jobId, that.jobId) && Objects.equals(schedulingId, that.schedulingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId, schedulingId);
    }
}
