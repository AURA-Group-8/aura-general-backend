package com.aura8.general_backend.clean_arch.application.usecase.job.create;

import com.aura8.general_backend.clean_arch.core.domain.Job;
import com.aura8.general_backend.clean_arch.core.gateway.JobGateway;
import com.aura8.general_backend.clean_arch.infraestructure.mapper.JobMapper;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {
    private final JobGateway jobRepository;

    public CreateJobUseCase(JobGateway jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job execute(CreateJobCommand jobCommand) {
        Job job = JobMapper.toDomain(jobCommand);
        return jobRepository.save(job);
    }
}
