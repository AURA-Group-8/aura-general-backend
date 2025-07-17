package com.aura8.general_backend.event;

import com.aura8.general_backend.entities.Scheduling;
import com.aura8.general_backend.entities.Users;
import com.aura8.general_backend.enums.UpdateTypeEnum;
import org.springframework.context.ApplicationEvent;

public class SchedulingDeletedEvent extends ApplicationEvent {

    private Scheduling scheduling;
    private String message;
    private Users user;
    private Boolean isAdmin;

    public SchedulingDeletedEvent(Object source, Scheduling scheduling, String message, Users user, Boolean isAdmin) {
        super(source);
        this.scheduling = scheduling;
        this.message = message;
        this.user = user;
        this.isAdmin = isAdmin;
    }

    public Users getUser() {
        return user;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public Scheduling getScheduling() {
        return scheduling;
    }

    public String getMessage() {
        return message;
    }
}
