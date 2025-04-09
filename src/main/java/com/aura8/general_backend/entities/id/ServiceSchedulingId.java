package com.aura8.general_backend.entities.id;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ServiceSchedulingId implements Serializable {

    private Integer schedulingId;
    private Integer serviceId;

    public ServiceSchedulingId() {
    }

    public ServiceSchedulingId(Integer schedulingId, Integer serviceId) {
        this.schedulingId = schedulingId;
        this.serviceId = serviceId;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(schedulingId, serviceId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceSchedulingId)) return false;
        ServiceSchedulingId that = (ServiceSchedulingId) o;
        return Objects.equals(schedulingId, that.schedulingId) &&
                Objects.equals(serviceId, that.serviceId);
    }
}
