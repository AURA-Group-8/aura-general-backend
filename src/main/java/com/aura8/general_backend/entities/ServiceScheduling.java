package com.aura8.general_backend.entities;

import com.aura8.general_backend.entities.id.ServiceSchedulingId;
import jakarta.persistence.*;

@Entity
public class ServiceScheduling {
    @EmbeddedId
    private ServiceSchedulingId id;

    @ManyToOne
    @JoinColumn(name = "schedulingId", referencedColumnName = "id", insertable = false, updatable = false)
    private Scheduling scheduling;

    @ManyToOne
    @JoinColumn(name = "serviceId", referencedColumnName = "id", insertable = false, updatable = false)
    private Service service;
    private Double currentPrice;
    private String observations;
    private Boolean discountApplied;

    public Scheduling getScheduling() {
        return scheduling;
    }

    public void setScheduling(Scheduling scheduling) {
        this.scheduling = scheduling;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
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