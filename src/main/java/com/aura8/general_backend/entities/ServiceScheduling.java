package com.aura8.general_backend.entities;

import com.aura8.general_backend.entities.id.ServiceSchedulingId;
import jakarta.persistence.*;

@Entity
public class ServiceScheduling {
    @EmbeddedId
    private ServiceSchedulingId id;

    @ManyToOne
    @MapsId("schedulingId")
    @JoinColumn(name = "scheduling_id")
    private Scheduling scheduling;

    @ManyToOne
    @MapsId("serviceId")
    @JoinColumn(name = "service_id")
    private Service service;

    private Double currentPrice;
    private String observations;
    private Boolean discountApplied;
}