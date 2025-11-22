package com.aura8.general_backend.clean_arch.infrastructure.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "job")
public class JobEntity extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private Integer expectedDurationMinutes;
    private Double price;
    private Boolean deleted = false;

    public JobEntity() {
    }

    public JobEntity(String name, String description, Integer expectedDurationMinutes, Double price) {
        this.name = name;
        this.description = description;
        this.expectedDurationMinutes = expectedDurationMinutes;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getExpectedDurationMinutes() {
        return expectedDurationMinutes;
    }

    public void setExpectedDurationMinutes(Integer expectedDurationMinutes) {
        this.expectedDurationMinutes = expectedDurationMinutes;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}

