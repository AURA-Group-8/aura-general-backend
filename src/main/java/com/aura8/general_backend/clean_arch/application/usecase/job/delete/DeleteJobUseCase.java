package com.aura8.general_backend.clean_arch.application.usecase.job.delete;

import com.aura8.general_backend.clean_arch.application.exception.ElementNotFoundException;
import com.aura8.general_backend.clean_arch.core.domain.Job;
import com.aura8.general_backend.clean_arch.core.gateway.JobGateway;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteJobUseCase {
    private final JobGateway jobRepository;

    public DeleteJobUseCase(JobGateway jobRepository) {
        this.jobRepository = jobRepository;
    }

    public void delete(Integer id) {
        Optional<Job> optionalJob = jobRepository.findById(id);
        if(optionalJob.isEmpty()) throw new ElementNotFoundException("Serviço de id: %d não encontrado".formatted(id));
        jobRepository.delete(id);
    }
}
