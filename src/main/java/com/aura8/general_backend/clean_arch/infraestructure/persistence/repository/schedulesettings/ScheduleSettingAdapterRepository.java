package com.aura8.general_backend.clean_arch.infraestructure.persistence.repository.schedulesettings;

import com.aura8.general_backend.clean_arch.application.exception.ElementNotFoundException;
import com.aura8.general_backend.clean_arch.core.domain.ScheduleSetting;
import com.aura8.general_backend.clean_arch.core.gateway.ScheduleSettingGateway;
import com.aura8.general_backend.clean_arch.infraestructure.mapper.ScheduleSettingMapper;
import com.aura8.general_backend.clean_arch.infraestructure.persistence.entity.ScheduleSettingEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ScheduleSettingAdapterRepository implements ScheduleSettingGateway {
    private final ScheduleSettingJpaRepository repository;

    public ScheduleSettingAdapterRepository(ScheduleSettingJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public ScheduleSetting patch(ScheduleSetting scheduleSetting) {
        Optional<ScheduleSettingEntity> optionalScheduleSettingEntity = repository.findById(1);
        if (optionalScheduleSettingEntity.isEmpty()) {
            throw new ElementNotFoundException("ScheduleSetting não foi encontrada");
        }

        ScheduleSettingEntity entity = optionalScheduleSettingEntity.get();
        ScheduleSettingMapper.merge(entity, scheduleSetting);
        ScheduleSettingEntity savedEntity = repository.save(entity);
        ScheduleSetting savedScheduleSetting = ScheduleSettingMapper.toDomain(savedEntity);
        return savedScheduleSetting;
    }

    @Override
    public ScheduleSetting findById(Integer id) {
        Optional<ScheduleSettingEntity> optionalScheduleSettingEntity = repository.findById(id);
        if (optionalScheduleSettingEntity.isEmpty()) {
            throw new ElementNotFoundException("ScheduleSetting não foi encontrada");
        }
        ScheduleSettingEntity entity = optionalScheduleSettingEntity.get();
        ScheduleSetting scheduleSetting = ScheduleSettingMapper.toDomain(entity);
        return scheduleSetting;
    }
}
