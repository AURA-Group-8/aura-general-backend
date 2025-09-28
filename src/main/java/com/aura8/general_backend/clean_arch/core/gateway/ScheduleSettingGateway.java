package com.aura8.general_backend.clean_arch.core.gateway;

import com.aura8.general_backend.clean_arch.core.domain.ScheduleSetting;

public interface ScheduleSettingGateway {
    ScheduleSetting patch(ScheduleSetting scheduleSetting);
    ScheduleSetting findById(Integer id);
}
