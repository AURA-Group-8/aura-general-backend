package com.aura8.general_backend.dtos.job;

import com.aura8.general_backend.entities.Job;
import org.springframework.data.domain.Page;

import java.util.List;

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
    public static Job toEntity(JobRequestDto jobRequestDto){
        if (jobRequestDto == null) return null;
        Job job = new Job();
        job.setName(jobRequestDto.getName());
        job.setDescription(jobRequestDto.getDescription());
        job.setExpectedDurationMinutes(jobRequestDto.getExpectedDurationMinutes());
        job.setPrice(jobRequestDto.getPrice());
        return job;
    }
}
