package com.aura8.general_backend.clean_arch.application.usecase.job.find.findall;

import com.aura8.general_backend.clean_arch.core.domain.Job;
import com.aura8.general_backend.clean_arch.core.gateway.JobGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllJobUseCase {
    private final JobGateway jobRepository;

    public FindAllJobUseCase(JobGateway jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<Job> findAll() {
        return jobRepository.findAll();
    }
}
