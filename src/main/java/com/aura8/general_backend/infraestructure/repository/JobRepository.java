package com.aura8.general_backend.infraestructure.repository;

import com.aura8.general_backend.infraestructure.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {

    Page<Job> findAllByDeletedFalse(Pageable pageable);
    Optional<Job> findByIdAndDeletedFalse(Integer id);

    @Query("SELECT SUM(j.price) FROM Job j WHERE j.id IN :jobsIds")
    Double sumPrices(@Param("jobsIds") List<Integer> jobsIds);

    @Query("SELECT SUM(j.expectedDurationMinutes) FROM Job j WHERE j.id IN :jobsIds")
    Long sumTimes(@Param("jobsIds") List<Integer> jobsIds);
}
