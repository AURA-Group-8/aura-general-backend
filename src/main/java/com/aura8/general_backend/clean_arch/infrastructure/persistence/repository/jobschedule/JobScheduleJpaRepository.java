package com.aura8.general_backend.clean_arch.infrastructure.persistence.repository.jobschedule;

import com.aura8.general_backend.clean_arch.infrastructure.persistence.entity.JobScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobScheduleJpaRepository extends JpaRepository<JobScheduleEntity, Integer> {

    @Query("SELECT js.job.name, COUNT(js) AS count " +
            "FROM JobScheduleEntity js " +
            "JOIN js.schedule s " +
            "WHERE s.canceled = false " +
            "GROUP BY js.job.name " +
            "ORDER BY count DESC " +
            "LIMIT 5"
    )
    List<String> getTopServicos();

    @Query("SELECT s.users.username, count(*) as agendamentos "
            + "FROM JobScheduleEntity js "
            + "JOIN js.schedule s "
            + "WHERE s.canceled = false "
            + "AND s.status = 'FEITO' "
            + "GROUP BY s.users.username "
            + "ORDER BY agendamentos DESC "
            + "LIMIT 5"
    )
    List<String> getTopClientes();
}
