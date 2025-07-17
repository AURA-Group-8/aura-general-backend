package com.aura8.general_backend.repository;

import com.aura8.general_backend.entities.SchedulingSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulingSettingsRepository extends JpaRepository<SchedulingSettings, Integer> {
}
