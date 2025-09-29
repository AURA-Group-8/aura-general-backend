package com.aura8.general_backend.clean_arch.application.usecase.job.patch;

import com.aura8.general_backend.clean_arch.application.exception.ElementNotFoundException;
import com.aura8.general_backend.clean_arch.application.usecase.job.find.findbyid.FindByIdJobUseCase;
import com.aura8.general_backend.clean_arch.core.domain.Job;
import com.aura8.general_backend.clean_arch.core.gateway.JobGateway;
import com.aura8.general_backend.clean_arch.infraestructure.mapper.JobMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatchJobUseCase {
    private final JobGateway jobRepository;
    private final FindByIdJobUseCase findByIdJobUseCase;

    public PatchJobUseCase(JobGateway jobRepository, FindByIdJobUseCase findByIdJobUseCase) {
        this.jobRepository = jobRepository;
        this.findByIdJobUseCase = findByIdJobUseCase;
    }

    public Job patch(PatchJobCommand patchJobCommand, Integer id) {
        Optional<Job> optionalJob = findByIdJobUseCase.findById(id);
        if (optionalJob.isEmpty()) {
            throw new ElementNotFoundException("Serviço de id: %d não encontrado".formatted(id));
        }
        Job job = optionalJob.get();
        JobMapper.mergeToDomain(job, patchJobCommand);
        return jobRepository.patch(job);
    }
}
