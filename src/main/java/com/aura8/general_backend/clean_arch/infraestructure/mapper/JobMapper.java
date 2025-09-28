package com.aura8.general_backend.clean_arch.infraestructure.mapper;

import com.aura8.general_backend.clean_arch.application.usecase.job.create.CreateJobCommand;
import com.aura8.general_backend.clean_arch.application.usecase.job.patch.PatchJobCommand;
import com.aura8.general_backend.clean_arch.core.domain.Job;
import com.aura8.general_backend.clean_arch.infraestructure.dto.job.*;
import com.aura8.general_backend.clean_arch.infraestructure.persistence.entity.JobEntity;

public class JobMapper {

    public static CreateJobCommand toCreateCommand(CreateJobRequest jobRequestDto) {
        return new CreateJobCommand(
                jobRequestDto.name(),
                jobRequestDto.description(),
                jobRequestDto.expectedDurationMinutes(),
                jobRequestDto.price()
        );
    }

    public static PatchJobCommand toPatchCommand(PatchJobRequest jobRequestDto) {
        return new PatchJobCommand(
                jobRequestDto.id(),
                jobRequestDto.name(),
                jobRequestDto.description(),
                jobRequestDto.expectedDurationMinutes(),
                jobRequestDto.price()
        );
    }

    public static CreateJobResponse toCreateResponse(Job job) {
        return new CreateJobResponse(
                job.getId(),
                job.getName(),
                job.getDescription(),
                job.getExpectedDurationMinutes(),
                job.getPrice()
        );
    }

    public static PatchJobResponse toPatchResponse(Job job) {
        return new PatchJobResponse(
                job.getId(),
                job.getName(),
                job.getDescription(),
                job.getExpectedDurationMinutes(),
                job.getPrice()
        );
    }

    public static JobEntity toEntity(Job job) {
        JobEntity jobEntity = new JobEntity(
                job.getName(),
                job.getDescription(),
                job.getExpectedDurationMinutes(),
                job.getPrice()
        );
        if(job.getId() != null) jobEntity.setId(job.getId());
        jobEntity.setDeleted(job.isDeleted());
        return jobEntity;
    }

    public static Job toDomain(JobEntity jobEntity) {
        Job job = new Job(
                jobEntity.getId(),
                jobEntity.getName(),
                jobEntity.getDescription(),
                jobEntity.getExpectedDurationMinutes(),
                jobEntity.getPrice()
        );
        return job;
    }

    public static Job toDomain(CreateJobCommand createJobCommand) {
        Job job = new Job(
                createJobCommand.name(),
                createJobCommand.description(),
                createJobCommand.expectedDurationMinutes(),
                createJobCommand.price()
        );
        return job;
    }

    public static Job toDomain(PatchJobCommand patchJobCommand) {
        Job job = new Job(
                patchJobCommand.id(),
                patchJobCommand.name(),
                patchJobCommand.description(),
                patchJobCommand.expectedDurationMinutes(),
                patchJobCommand.price()
        );
        return job;
    }

    public static FindJobResponse toFindResponse(Job job) {
        return new FindJobResponse(
                job.getId(),
                job.getName(),
                job.getDescription(),
                job.getExpectedDurationMinutes(),
                job.getPrice()
        );
    }

    public static void mergeToDomain(Job job, PatchJobCommand patchJobCommand) {
        if (patchJobCommand.name() != null) {
            job.setName(patchJobCommand.name());
        }
        if (patchJobCommand.description() != null) {
            job.setDescription(patchJobCommand.description());
        }
        if (patchJobCommand.expectedDurationMinutes() != null) {
            job.setExpectedDurationMinutes(patchJobCommand.expectedDurationMinutes());
        }
        if (patchJobCommand.price() != null) {
            job.setPrice(patchJobCommand.price());
        }
    }

    public static void mergeToEntity(JobEntity jobEntity, Job job) {
        if (job.getName() != null) {
            jobEntity.setName(job.getName());
        }
        if (job.getDescription() != null) {
            jobEntity.setDescription(job.getDescription());
        }
        if (job.getExpectedDurationMinutes() != null) {
            jobEntity.setExpectedDurationMinutes(job.getExpectedDurationMinutes());
        }
        if (job.getPrice() != null) {
            jobEntity.setPrice(job.getPrice());
        }
    }
}
