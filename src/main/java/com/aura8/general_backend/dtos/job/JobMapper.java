package com.aura8.general_backend.dtos.job;

import com.aura8.general_backend.entities.Job;
import org.springframework.data.domain.Page;

public class JobMapper {
    public static JobResponseDto toResponse(Job job){
        return new JobResponseDto(
                job.getId(),
                job.getName(),
                job.getDescription(),
                job.getExpectedDurationMinutes(),
                job.getPrice()
        );
    }

    public static Page<JobResponseDto> toResponse(Page<Job> jobsPage){
        if(jobsPage == null) return null;
        Page<JobResponseDto> jobsDtoPage = jobsPage.map(job -> toResponse(job));
        return jobsDtoPage;
    }
}
