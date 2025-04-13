package com.aura8.general_backend.entities.id;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ServiceSchedulingId implements Serializable {
    private Integer serviceId;
    private Integer schedulingId;

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
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
        if (!(o instanceof ServiceSchedulingId that)) return false;
        return Objects.equals(getServiceId(), that.getServiceId()) && Objects.equals(getSchedulingId(), that.getSchedulingId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getServiceId(), getSchedulingId());
    }
}
