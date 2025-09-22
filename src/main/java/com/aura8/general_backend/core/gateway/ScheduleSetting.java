package com.aura8.general_backend.core.gateway;

import com.aura8.general_backend.infraestructure.entities.SchedulingSettings;

public interface ScheduleSetting {
    SchedulingSettings update(ScheduleSetting scheduleSetting);
    SchedulingSettings findById();
}
