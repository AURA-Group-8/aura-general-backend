package com.aura8.general_backend.clean_arch.infraestructure.persistence.repository.job;

import com.aura8.general_backend.clean_arch.infraestructure.persistence.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobJpaRepository extends JpaRepository<JobEntity, Integer> {
    List<JobEntity> findAllByDeletedFalse();

    Optional<JobEntity> findByIdAndDeletedFalse(Integer id);
}
