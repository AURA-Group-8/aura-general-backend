package com.aura8.general_backend.clean_arch.infrastructure.persistence.repository.jobschedule;

import com.aura8.general_backend.clean_arch.core.domain.Job;
import com.aura8.general_backend.clean_arch.core.domain.JobSchedule;
import com.aura8.general_backend.clean_arch.core.domain.Schedule;
import com.aura8.general_backend.clean_arch.core.domain.Users;
import com.aura8.general_backend.clean_arch.core.gateway.JobScheduleGateway;
import com.aura8.general_backend.clean_arch.infrastructure.mapper.JobScheduleMapper;
import com.aura8.general_backend.clean_arch.infrastructure.persistence.entity.JobScheduleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JobScheduleAdapterRepository implements JobScheduleGateway {

    private final JobScheduleJpaRepository repository;

    public JobScheduleAdapterRepository(JobScheduleJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public JobSchedule create(JobSchedule jobScheduling) {
        return null;
    }

    @Override
    public List<JobSchedule> create(Schedule schedule, List<Job> jobs) {
        if(jobs.isEmpty()) {
            throw new IllegalArgumentException("Jobs não pode ser nulo");
        }
        if(schedule == null) {
            throw new IllegalArgumentException("Schedule não pode ser nulo");
        }
        List<JobSchedule> jobSchedules = jobs.stream().map(job -> {
            JobSchedule jobSchedule = new JobSchedule(
                    schedule,
                    job,
                    job.getPrice()
            );
            return jobSchedule;
        }).toList();
        List<JobScheduleEntity> jobSchedulingEntities = jobSchedules.stream()
                .map(JobScheduleMapper::toEntity)
                .toList();
        List<JobScheduleEntity> jobScheduleEntities = repository.saveAll(jobSchedulingEntities);
        List<JobSchedule> savedJobSchedules = jobScheduleEntities.stream()
                .map(JobScheduleMapper::toDomain)
                .toList();
        return savedJobSchedules;
    }

    @Override
    public JobSchedule findById(Integer id) {
        return null;
    }

    @Override
    public List<JobSchedule> findAllByScheduleId(Integer scheduleId) {
        return List.of();
    }

    @Override
    public List<String> findTopServicos() {
        List<String> topServicos = repository.getTopServicos();
        topServicos = topServicos.stream()
                .map(servico -> servico.split(",")[0])
                .toList();
        System.out.println("Top Serviços: " + topServicos);
        return topServicos;
    }

    @Override
    public List<String> findTopUsers() {
        List<String> topServicos = repository.getTopClientes();
        topServicos = topServicos.stream()
                .map(servico -> servico.split(",")[0])
                .toList();
        System.out.println("Top Serviços: " + topServicos);
        return topServicos;
    }
}
