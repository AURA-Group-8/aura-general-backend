package com.aura8.general_backend.clean_arch.application.usecase.job.find.findbyid;

import com.aura8.general_backend.clean_arch.core.domain.Job;
import com.aura8.general_backend.clean_arch.core.gateway.JobGateway;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindByIdJobUseCase {
    private final JobGateway jobRepository;

    public FindByIdJobUseCase(JobGateway jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Optional<Job> findById(Integer id) {
        return jobRepository.findById(id);
    }
}
