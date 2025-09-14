package com.aura8.general_backend.infraestructure.entities;

import com.aura8.general_backend.infraestructure.entities.id.JobSchedulingId;
import jakarta.persistence.*;

@Entity
public class JobScheduling {
    @EmbeddedId
    private JobSchedulingId id;

    @ManyToOne
    @JoinColumn(name = "schedulingId", referencedColumnName = "id", insertable = false, updatable = false)
    private Scheduling scheduling;

    @ManyToOne
    @JoinColumn(name = "jobId", referencedColumnName = "id", insertable = false, updatable = false)
    private Job job;
    private Double currentPrice;
    private String observations;
    private Boolean discountApplied;

    public JobSchedulingId getId() {
        return id;
    }

    public void setId(JobSchedulingId id) {
        this.id = id;
    }

    public Scheduling getScheduling() {
        return scheduling;
    }

    public void setScheduling(Scheduling scheduling) {
        this.scheduling = scheduling;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Boolean getDiscountApplied() {
        return discountApplied;
    }

    public void setDiscountApplied(Boolean discountApplied) {
        this.discountApplied = discountApplied;
    }
}