package com.aura8.general_backend.clean_arch.application.usecase.schedulesettings.find;

import com.aura8.general_backend.clean_arch.core.domain.ScheduleSetting;
import com.aura8.general_backend.clean_arch.infrastructure.persistence.repository.schedulesettings.ScheduleSettingAdapterRepository;
import org.springframework.stereotype.Service;

@Service
public class FindPrincipalScheduleSettingUseCase {
    private final ScheduleSettingAdapterRepository repository;

    public FindPrincipalScheduleSettingUseCase(ScheduleSettingAdapterRepository repository) {
        this.repository = repository;
    }

    public ScheduleSetting find() {
        return repository.findById(1);
    }
}
