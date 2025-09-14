package com.aura8.general_backend.event;

import com.aura8.general_backend.infraestructure.entities.Scheduling;
import com.aura8.general_backend.infraestructure.entities.Users;
import org.springframework.context.ApplicationEvent;

public class SchedulingCreateEvent extends ApplicationEvent {
    private final Boolean isAdminScheduling;
    private final Users user;
    private final Scheduling scheduling;

    public SchedulingCreateEvent(Object source, Boolean isAdminScheduling, Users user, Scheduling scheduling) {
        super(source);
        this.isAdminScheduling = isAdminScheduling;
        this.user = user;
        this.scheduling = scheduling;
    }

    public Boolean getAdminScheduling() {
        return isAdminScheduling;
    }

    public Users getUser() {
        return user;
    }

    public Scheduling getScheduling() {
        return scheduling;
    }
}
