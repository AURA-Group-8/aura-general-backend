package com.aura8.general_backend.clean_arch.application.usecase.schedulesettings.patch;

import com.aura8.general_backend.clean_arch.application.usecase.schedulesettings.find.FindPrincipalScheduleSettingUseCase;
import com.aura8.general_backend.clean_arch.core.domain.ScheduleSetting;
import com.aura8.general_backend.clean_arch.core.gateway.ScheduleSettingGateway;
import com.aura8.general_backend.clean_arch.infraestructure.mapper.ScheduleSettingMapper;
import org.springframework.stereotype.Service;

@Service
public class PatchScheduleSettingUseCase {
    private final ScheduleSettingGateway repository;
    private final FindPrincipalScheduleSettingUseCase findPrincipalScheduleSettingUseCase;

    public PatchScheduleSettingUseCase(ScheduleSettingGateway repository, FindPrincipalScheduleSettingUseCase findPrincipalScheduleSettingUseCase) {
        this.repository = repository;
        this.findPrincipalScheduleSettingUseCase = findPrincipalScheduleSettingUseCase;
    }

    public ScheduleSetting patch(PatchScheduleSettingCommand patchScheduleSettingCommand) {
        ScheduleSetting scheduleSetting = findPrincipalScheduleSettingUseCase.find();
        ScheduleSettingMapper.merge(scheduleSetting, patchScheduleSettingCommand);
        return repository.patch(scheduleSetting);
    }
}
