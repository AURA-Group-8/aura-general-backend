package com.aura8.general_backend.clean_arch.infraestructure.persistence.repository.job;

import com.aura8.general_backend.clean_arch.application.exception.ElementNotFoundException;
import com.aura8.general_backend.clean_arch.core.domain.Job;
import com.aura8.general_backend.clean_arch.core.gateway.JobGateway;
import com.aura8.general_backend.clean_arch.infraestructure.persistence.entity.JobEntity;
import com.aura8.general_backend.clean_arch.infraestructure.mapper.JobMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JobAdapterRepository implements JobGateway {
    private final JobJpaRepository repository;

    public JobAdapterRepository(JobJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Job save(Job job) {
        JobEntity entity = JobMapper.toEntity(job);
        JobEntity savedJobEntity = repository.save(entity);
        Job savedJob = JobMapper.toDomain(savedJobEntity);
        return savedJob;
    }

    @Override
    public List<Job> findAll() {
        List<JobEntity> jobsEntities = repository.findAllByDeletedFalse();
        if (jobsEntities.isEmpty()) {
            return List.of();
        }
        List<Job> jobs = jobsEntities.stream().map(JobMapper::toDomain).toList();
        return jobs;
    }

    @Override
    public Optional<Job> findById(Integer id) {
        Optional<JobEntity> jobEntity = repository.findByIdAndDeletedFalse(id);
        if (jobEntity.isEmpty()) {
            return Optional.empty();
        }
        Job job = JobMapper.toDomain(jobEntity.get());
        return Optional.of(job);
    }

    @Override
    public Job patch(Job job) {
        Optional<JobEntity> jobEntityOptional = repository.findByIdAndDeletedFalse(job.getId());
        if (jobEntityOptional.isEmpty()) throw new ElementNotFoundException("Serviço de id: %d não encontrado".formatted(job.getId()));
        JobEntity jobEntity = jobEntityOptional.get();
        JobMapper.mergeToEntity(jobEntity, job);
        JobEntity updatedJobEntity = repository.save(jobEntity);
        return JobMapper.toDomain(updatedJobEntity);
    }


    @Override
    public void delete(Integer id) {
        Optional<JobEntity> jobEntityOptional = repository.findByIdAndDeletedFalse(id);
        if (jobEntityOptional.isEmpty()) throw new ElementNotFoundException("Serviço de id: %d não encontrado".formatted(id));
        JobEntity jobEntity = jobEntityOptional.get();
        jobEntity.setDeleted(true);
        repository.save(jobEntity);
    }
}
