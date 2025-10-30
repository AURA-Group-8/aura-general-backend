package com.aura8.general_backend.clean_arch.infrastructure.persistence.repository.jobschedule;

import com.aura8.general_backend.clean_arch.infrastructure.persistence.entity.JobScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobScheduleJpaRepository extends JpaRepository<JobScheduleEntity, Integer> {
}
