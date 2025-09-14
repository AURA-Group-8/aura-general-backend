package com.aura8.general_backend.core.gateway;

import com.aura8.general_backend.core.domain.Job;

import java.util.List;

public interface JobGateway {
    Job save(Job job);
    List<Job> findAll();
    Job findById(Integer id);
    Job update(Job job);
    Job delete(Job job);
}
