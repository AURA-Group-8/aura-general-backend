package com.aura8.general_backend.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ServiceScheduling {
    @Id
    private Integer serviceId;
    @Id
    private Integer schedulingId;
    private Double currentPrice;
    private String observations;
    private Boolean discountApplied;
}