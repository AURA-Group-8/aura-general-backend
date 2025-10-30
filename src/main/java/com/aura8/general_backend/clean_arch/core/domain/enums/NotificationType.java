package com.aura8.general_backend.clean_arch.core.domain.enums;

public enum NotificationType {
    SCHEDULE_CONFIRMED,
    SCHEDULE_CANCELED,
    RATE_SERVICE,
    FIRST_SCHEDULE_COMPLETED;

    private String message;
    private String actor;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = this.message;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }
}
