package com.aura8.general_backend.clean_arch.core.gateway;

import com.aura8.general_backend.infraestructure.entities.JobScheduling;

import java.util.List;

public interface JobSchedulingGateway {
    JobScheduling create(JobScheduling jobScheduling);
    JobScheduling findById(Integer id);
    void delete(Integer id);
    JobScheduling update(JobScheduling jobScheduling);
    List<JobScheduling> findAllByScheduleId(Integer scheduleId);
    List<JobScheduling> findTopServicos();
}
