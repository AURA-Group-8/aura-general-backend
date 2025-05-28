package com.aura8.general_backend.repository;

import com.aura8.general_backend.entities.Scheduling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SchedulingRepository extends JpaRepository<Scheduling, Integer> {
    List<Scheduling> findByStartDatetimeBetween(LocalDateTime startDate, LocalDateTime endDate);
}
