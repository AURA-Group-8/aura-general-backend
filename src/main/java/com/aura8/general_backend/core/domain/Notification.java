package com.aura8.general_backend.core.domain;

public class Notification {
    private Integer id;
    private Users users;
    private Schedule schedule;
    private String message;
    private Boolean hasButtonToRate;
    private Boolean isAnswered = false;
    private Boolean isRead = false;

    public Notification(Boolean hasButtonToRate, String message, Schedule schedule, Users users, Integer id) {
        this.hasButtonToRate = hasButtonToRate;
        this.message = message;
        this.schedule = schedule;
        this.users = users;
        this.id = id;
    }

    public Notification(Integer id, Users users, Schedule schedule, String message, Boolean hasButtonToRate, Boolean isAnswered, Boolean isRead) {
        this.id = id;
        this.users = users;
        this.schedule = schedule;
        this.message = message;
        this.hasButtonToRate = hasButtonToRate;
        this.isAnswered = isAnswered;
        this.isRead = isRead;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Schedule getScheduling() {
        return schedule;
    }

    public void setScheduling(Schedule schedule) {
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

    public Boolean getAnswered() {
        return isAnswered;
    }

    public void setAnswered(Boolean answered) {
        isAnswered = answered;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }
}
