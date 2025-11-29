package com.aura8.general_backend.clean_arch.core.gateway;

import com.aura8.general_backend.clean_arch.core.domain.Job;
import com.aura8.general_backend.clean_arch.core.domain.JobSchedule;
import com.aura8.general_backend.clean_arch.core.domain.Schedule;

import java.util.List;

public interface JobScheduleGateway {
    List<JobSchedule> create(Schedule schedule, List<Job> jobs);
    List<JobSchedule> findAllByScheduleId(Integer scheduleId);
    List<String> findTopServicos();
    List<String> findTopUsers();
}
