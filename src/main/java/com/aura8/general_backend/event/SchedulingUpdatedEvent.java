package com.aura8.general_backend.event;

import com.aura8.general_backend.infraestructure.entities.Scheduling;
import com.aura8.general_backend.infraestructure.entities.Users;
import com.aura8.general_backend.enums.UpdateTypeEnum;
import org.springframework.context.ApplicationEvent;

public class SchedulingUpdatedEvent extends ApplicationEvent {

    private Scheduling scheduling;
    private Users user;
    private Boolean hasButton;
    private UpdateTypeEnum updateType;

    public SchedulingUpdatedEvent(Object source, Scheduling scheduling, Users user, Boolean hasButton, UpdateTypeEnum updateType) {
        super(source);
        this.scheduling = scheduling;
        this.user = user;
        this.hasButton = hasButton;
        this.updateType = updateType;
    }

    public Scheduling getScheduling() {
        return scheduling;
    }

    public Users getUser() {
        return user;
    }

    public Boolean getHasButton() {
        return hasButton;
    }

    public UpdateTypeEnum getUpdateType() {
        return updateType;
    }
}
