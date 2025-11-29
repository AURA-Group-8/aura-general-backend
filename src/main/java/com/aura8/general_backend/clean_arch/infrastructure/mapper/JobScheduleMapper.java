package com.aura8.general_backend.clean_arch.infrastructure.mapper;

import com.aura8.general_backend.clean_arch.core.domain.JobSchedule;
import com.aura8.general_backend.clean_arch.core.domain.Job;
import com.aura8.general_backend.clean_arch.core.domain.Schedule;
import com.aura8.general_backend.clean_arch.infrastructure.persistence.entity.JobScheduleEntity;
import com.aura8.general_backend.clean_arch.infrastructure.persistence.entity.JobEntity;
import com.aura8.general_backend.clean_arch.infrastructure.persistence.entity.ScheduleEntity;
import com.aura8.general_backend.clean_arch.infrastructure.persistence.entity.id.JobScheduleId;

public class JobScheduleMapper {
    public static JobScheduleEntity toEntity(JobSchedule jobSchedule) {
        ScheduleEntity scheduleEntity = ScheduleMapper.toEntity(jobSchedule.getScheduling());
        JobEntity jobEntity = JobMapper.toEntity(jobSchedule.getJob());

        JobScheduleId id = new JobScheduleId(scheduleEntity.getId(), jobEntity.getId());
        JobScheduleEntity entity = new JobScheduleEntity(
                id,
                scheduleEntity,
                jobEntity,
                jobSchedule.getCurrentPrice()
        );
        return entity;
    }

    public static JobSchedule toDomain(JobScheduleEntity entity) {
        Schedule schedule = ScheduleMapper.toDomainWithoutJobSchedule(entity.getSchedule());
        Job job = JobMapper.toDomain(entity.getJob());
        Integer id = null;
        if (entity.getId() != null) {
            id = entity.getId().getScheduleId();
        }
        return new JobSchedule(id, schedule, job, entity.getCurrentPrice());
    }
}
