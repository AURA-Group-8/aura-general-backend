package com.aura8.general_backend.clean_arch.core.gateway;

import com.aura8.general_backend.clean_arch.core.domain.Schedule;
import com.aura8.general_backend.clean_arch.core.domain.Users;

public interface EventPublisherGateway {
    void publishEventCreateSchedule(Schedule schedule, Users user);
}
