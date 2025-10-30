package com.aura8.general_backend.clean_arch.infrastructure.persistence.repository.schedule;

import com.aura8.general_backend.clean_arch.infrastructure.persistence.entity.ScheduleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleJpaRepository extends JpaRepository<ScheduleEntity, Integer> {
    List<ScheduleEntity> findByStartDatetimeBetweenAndCanceledFalse(LocalDateTime startDate, LocalDateTime endDate);

    Optional<ScheduleEntity> findByIdAndCanceledFalse(Integer id);

    Page<ScheduleEntity> findAllByCanceledFalse(Pageable pageable);
}
