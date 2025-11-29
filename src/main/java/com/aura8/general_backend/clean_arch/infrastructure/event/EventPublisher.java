package com.aura8.general_backend.clean_arch.infrastructure.event;

import com.aura8.general_backend.clean_arch.core.domain.Schedule;
import com.aura8.general_backend.clean_arch.core.domain.Users;
import com.aura8.general_backend.clean_arch.core.gateway.EventPublisherGateway;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventPublisher implements EventPublisherGateway {

    private final ApplicationEventPublisher applicationEventPublisher;

    public EventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publishEventCreateSchedule(Schedule schedule, Users user) {
        ScheduleCreateEvent event = new ScheduleCreateEvent(this, schedule, user);
        applicationEventPublisher.publishEvent(event);
    }
}
