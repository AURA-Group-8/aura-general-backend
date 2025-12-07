package com.aura8.general_backend.clean_arch.infrastructure.persistence.repository.schedule;

import com.aura8.general_backend.clean_arch.infrastructure.persistence.entity.ScheduleEntity;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ScheduleCacheRepository {
    private final ScheduleJpaRepository repository;

    public ScheduleCacheRepository(ScheduleJpaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Cacheable(value = "schedulesByDateCache")
    public List<ScheduleEntity> findByStartDatetimeBetweenAndCanceled(LocalDateTime startDate, LocalDateTime endDate, boolean canceled) {
        List<ScheduleEntity> scheduleEntities = repository.findByStartDatetimeBetweenAndCanceled(startDate, endDate, canceled);
        scheduleEntities.forEach(s -> Hibernate.initialize(s.getJobSchedules()));
        return scheduleEntities;
    }
}
