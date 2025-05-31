package com.aura8.general_backend.service;

import com.aura8.general_backend.dtos.jobscheduling.AvailableDayDto;
import com.aura8.general_backend.entities.Job;
import com.aura8.general_backend.entities.JobScheduling;
import com.aura8.general_backend.entities.Scheduling;
import com.aura8.general_backend.entities.id.JobSchedulingId;
import com.aura8.general_backend.exception.ElementNotFoundException;
import com.aura8.general_backend.repository.JobSchedulingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobSchedulingService {
    private final JobSchedulingRepository jobSchedulingRepository;
    private final SchedulingService schedulingService;
    private final JobService jobService;

    public JobSchedulingService(JobSchedulingRepository jobSchedulingRepository, SchedulingService schedulingService, JobService jobService) {
        this.jobSchedulingRepository = jobSchedulingRepository;
        this.schedulingService = schedulingService;
        this.jobService = jobService;
    }

    public Scheduling create(Integer userId, List<Integer> jobsIds, LocalDateTime startDatetime){
        if (jobsIds == null || jobsIds.isEmpty()) {
            throw new ElementNotFoundException("Lista de serviços está vazia.");
        }
        List<Job> jobs = jobService.getJobsInList(jobsIds);
        Double totalPrice = jobService.getTotalPrice(jobsIds);
        LocalDateTime endDatetime = startDatetime.plusMinutes(jobService.getTotalTime(jobsIds));
        Scheduling scheduling = new Scheduling();
        scheduling.setStartDatetime(startDatetime);
        scheduling.setEndDatetime(endDatetime);
        scheduling.setTotalPrice(totalPrice);
        Scheduling newScheduling = schedulingService.create(scheduling,userId);
        jobs.forEach(job -> {
            JobScheduling newJobScheduling = new JobScheduling();
            JobSchedulingId jobSchedulingId = new JobSchedulingId();
            jobSchedulingId.setJobId(job.getId());
            jobSchedulingId.setSchedulingId(newScheduling.getId());
            newJobScheduling.setId(jobSchedulingId);
            newJobScheduling.setScheduling(newScheduling);
            newJobScheduling.setJob(job);
            newJobScheduling.setDiscountApplied(false);
            newJobScheduling.setCurrentPrice(job.getPrice());
            jobSchedulingRepository.save(newJobScheduling);
        });
        return newScheduling;
    }

    public List<Job> getJobsInScheduling(Integer schedulingId) {
        Scheduling scheduling = schedulingService.findById(schedulingId);
        return jobSchedulingRepository.findAllByScheduling(scheduling)
                .stream()
                .map(JobScheduling::getJob)
                .toList();
    }
}
