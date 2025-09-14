package com.aura8.general_backend.infraestructure.repository;

import com.aura8.general_backend.infraestructure.entities.JobScheduling;
import com.aura8.general_backend.infraestructure.entities.Scheduling;
import com.aura8.general_backend.infraestructure.entities.id.JobSchedulingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSchedulingRepository extends JpaRepository<JobScheduling, JobSchedulingId> {
    List<JobScheduling> findAllByScheduling(Scheduling scheduling);

    @Query("SELECT js.job.name, COUNT(js) AS count FROM JobScheduling js " +
            "JOIN js.scheduling s " +
            "WHERE s.isCanceled = false " +
            "GROUP BY js.job.name " +
            "ORDER BY count DESC " +
            "LIMIT 5"
    )
    List<String> getTopServicos();
}
