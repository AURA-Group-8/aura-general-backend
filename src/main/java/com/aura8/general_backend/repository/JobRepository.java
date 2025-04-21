package com.aura8.general_backend.repository;

import com.aura8.general_backend.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    @Query("SELECT SUM(j.price) FROM Job j WHERE j.id IN :jobsIds")
    Double sumPrices(@Param("jobsIds") List<Integer> jobsIds);

    @Query("SELECT SUM(j.expectedDurationMinutes) FROM Job j WHERE j.id IN :jobsIds")
    Long sumTimes(@Param("jobsIds") List<Integer> jobsIds);
}
