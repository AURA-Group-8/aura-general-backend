package com.aura8.general_backend.clean_arch.infraestructure.persistence.repository.schedulesettings;

import com.aura8.general_backend.clean_arch.infraestructure.persistence.entity.ScheduleSettingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleSettingJpaRepository extends JpaRepository<ScheduleSettingEntity, Integer> {
}
