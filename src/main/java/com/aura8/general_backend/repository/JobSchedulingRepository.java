package com.aura8.general_backend.repository;

import com.aura8.general_backend.entities.JobScheduling;
import com.aura8.general_backend.entities.Scheduling;
import com.aura8.general_backend.entities.id.JobSchedulingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSchedulingRepository extends JpaRepository<JobScheduling, JobSchedulingId> {
    List<JobScheduling> findAllByScheduling(Scheduling scheduling);
}
