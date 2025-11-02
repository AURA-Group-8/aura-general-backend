package com.aura8.general_backend.clean_arch.application.usecase.schedulesettings.patch;

import com.aura8.general_backend.clean_arch.core.domain.ScheduleSetting;
import com.aura8.general_backend.clean_arch.core.gateway.ScheduleSettingGateway;
import com.aura8.general_backend.clean_arch.infrastructure.mapper.ScheduleSettingMapper;
import org.springframework.stereotype.Service;

@Service
public class PatchScheduleSettingUseCase {
    private final ScheduleSettingGateway repository;

    public PatchScheduleSettingUseCase(ScheduleSettingGateway repository) {
        this.repository = repository;
    }

    public ScheduleSetting patch(PatchScheduleSettingCommand patchScheduleSettingCommand) {
        ScheduleSetting scheduleSetting = repository.findById(1);
        ScheduleSettingMapper.merge(scheduleSetting, patchScheduleSettingCommand);
        return repository.patch(scheduleSetting);
    }
}
