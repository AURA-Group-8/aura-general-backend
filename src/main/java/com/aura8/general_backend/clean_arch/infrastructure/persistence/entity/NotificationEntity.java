package com.aura8.general_backend.clean_arch.infrastructure.persistence.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "notification")
public class NotificationEntity extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private UsersEntity users;
    @ManyToOne
    private ScheduleEntity schedule;
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

    public UsersEntity getUsers() {
        return users;
    }

    public void setUsers(UsersEntity users) {
        this.users = users;
    }

    public ScheduleEntity getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleEntity schedule) {
        this.schedule = schedule;
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
