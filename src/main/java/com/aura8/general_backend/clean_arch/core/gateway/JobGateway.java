package com.aura8.general_backend.clean_arch.core.gateway;

import com.aura8.general_backend.clean_arch.core.domain.Job;

import java.util.List;
import java.util.Optional;

public interface JobGateway {
    Job save(Job job);
    List<Job> findAll();
    Optional<Job> findById(Integer id);
    Job patch(Job job);
    void delete(Integer id);
}
