package com.aura8.general_backend.clean_arch.infraestructure.persistence.entity;

import com.aura8.general_backend.infraestructure.entities.AuditableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class NotificationEntity extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    private Integer schedulingId;
    private String message;
    private Boolean hasButtonToRate;
    private Boolean wasAnswered = false;
    private Boolean wasRead = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSchedulingId() {
        return schedulingId;
    }

    public void setSchedulingId(Integer schedulingId) {
        this.schedulingId = schedulingId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getHasButtonToRate() {
        return hasButtonToRate;
    }

    public void setHasButtonToRate(Boolean hasButtonToRate) {
        this.hasButtonToRate = hasButtonToRate;
    }

    public Boolean getWasAnswered() {
        return wasAnswered;
    }

    public void setWasAnswered(Boolean wasAnswered) {
        this.wasAnswered = wasAnswered;
    }

    public Boolean getWasRead() {
        return wasRead;
    }

    public void setWasRead(Boolean wasRead) {
        this.wasRead = wasRead;
    }
}
