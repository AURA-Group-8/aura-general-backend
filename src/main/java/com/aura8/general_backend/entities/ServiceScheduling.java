package com.aura8.general_backend.entities;

import jakarta.persistence.*;

@Entity
public class ServiceScheduling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer schedulingId;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer serviceId;
    private Double currentPrice;
    private String observations;
    private Boolean discountApplied;

    public Integer getSchedulingId() {
        return schedulingId;
    }

    public void setSchedulingId(Integer schedulingId) {
        this.schedulingId = schedulingId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
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