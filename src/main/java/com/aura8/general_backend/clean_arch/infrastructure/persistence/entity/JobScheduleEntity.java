package com.aura8.general_backend.clean_arch.infrastructure.persistence.entity;

import com.aura8.general_backend.clean_arch.infrastructure.persistence.entity.id.JobScheduleId;
import jakarta.persistence.*;

@Entity
public class JobScheduleEntity {
    @EmbeddedId
    private JobScheduleId id;

    @ManyToOne
    @MapsId("scheduleId")
    @JoinColumn(name = "scheduleId", referencedColumnName = "id", insertable = false, updatable = false)
    private ScheduleEntity schedule;

    @ManyToOne
    @MapsId("jobId")
    @JoinColumn(name = "jobId", referencedColumnName = "id", insertable = false, updatable = false)
    private JobEntity job;
    private Double currentPrice;

    public JobScheduleEntity(JobScheduleId id, ScheduleEntity schedule, JobEntity job, Double currentPrice) {
        this.id = id;
        this.schedule = schedule;
        this.job = job;
        this.currentPrice = currentPrice;
    }

    public JobScheduleEntity() {
    }

    public JobScheduleEntity(ScheduleEntity schedule, JobEntity job, Double currentPrice) {
        this.schedule = schedule;
        this.job = job;
        this.currentPrice = currentPrice;
    }

    public JobScheduleId getId() {
        return id;
    }

    public void setId(JobScheduleId id) {
        this.id = id;
    }

    public ScheduleEntity getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleEntity schedule) {
        this.schedule = schedule;
    }

    public JobEntity getJob() {
        return job;
    }

    public void setJob(JobEntity job) {
        this.job = job;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

}