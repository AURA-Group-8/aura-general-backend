package com.aura8.general_backend.clean_arch.infrastructure.persistence.repository.schedule;

import com.aura8.general_backend.clean_arch.infrastructure.persistence.entity.ScheduleEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleJpaRepository extends JpaRepository<ScheduleEntity, Integer> {
    @Transactional
    List<ScheduleEntity> findByStartDatetimeBetweenAndCanceled(LocalDateTime startDate, LocalDateTime endDate, boolean canceled);

    Optional<ScheduleEntity> findByIdAndCanceled(Integer id, boolean canceled);

    Page<ScheduleEntity> findAllByCanceled(Pageable pageable, boolean canceled);

    List<ScheduleEntity> findByUsersIdAndCanceled(Integer userId, boolean canceled);
}
