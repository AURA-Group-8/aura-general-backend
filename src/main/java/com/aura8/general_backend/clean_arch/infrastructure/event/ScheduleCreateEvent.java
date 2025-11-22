package com.aura8.general_backend.clean_arch.infrastructure.event;

import com.aura8.general_backend.clean_arch.core.domain.Schedule;
import com.aura8.general_backend.clean_arch.core.domain.Users;
import org.springframework.context.ApplicationEvent;

public class ScheduleCreateEvent extends ApplicationEvent {
    private final Schedule schedule;
    private final Users user;

    public ScheduleCreateEvent(Object source, Schedule schedule, Users user) {
        super(source);
        this.schedule = schedule;
        this.user = user;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Users getUser() {
        return user;
    }
}
