package com.aura8.general_backend.clean_arch.core.gateway;

import com.aura8.general_backend.clean_arch.core.domain.Job;
import com.aura8.general_backend.clean_arch.core.domain.JobSchedule;
import com.aura8.general_backend.clean_arch.core.domain.Schedule;

import java.util.List;

public interface JobScheduleGateway {
    JobSchedule create(JobSchedule jobScheduling);
    List<JobSchedule> create(Schedule schedule, List<Job> jobs);
    JobSchedule findById(Integer id);
    List<JobSchedule> findAllByScheduleId(Integer scheduleId);
    List<JobSchedule> findTopServicos();
}
