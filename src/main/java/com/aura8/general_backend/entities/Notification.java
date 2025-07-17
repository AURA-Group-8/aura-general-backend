package com.aura8.general_backend.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    private Integer schedulingId;
    private String message;
    private Boolean hasButtonToRate;
    private Boolean wasAnswered;
    private final LocalDateTime created_at = LocalDateTime.now();

    public Boolean getWasAnswered() {
        return wasAnswered;
    }

    public void setWasAnswered(Boolean wasAnswered) {
        this.wasAnswered = wasAnswered;
    }

    public Integer getSchedulingId() {
        return schedulingId;
    }

    public void setSchedulingId(Integer schedulingId) {
        this.schedulingId = schedulingId;
    }

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

    public LocalDateTime getCreated_at() {
        return created_at;
    }
}
