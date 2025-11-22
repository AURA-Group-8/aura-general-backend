package com.aura8.general_backend.clean_arch.infrastructure.mapper;

import com.aura8.general_backend.clean_arch.application.usecase.schedule.create.CreateScheduleCommand;
import com.aura8.general_backend.clean_arch.application.usecase.schedule.patch.PatchScheduleCommand;
import com.aura8.general_backend.clean_arch.core.domain.AvailableDay;
import com.aura8.general_backend.clean_arch.core.domain.Job;
import com.aura8.general_backend.clean_arch.core.domain.JobSchedule;
import com.aura8.general_backend.clean_arch.core.domain.Schedule;
import com.aura8.general_backend.clean_arch.infrastructure.dto.schedule.*;
import com.aura8.general_backend.clean_arch.infrastructure.persistence.entity.ScheduleEntity;

import java.util.List;

public class ScheduleMapper {

    public static Schedule toDomainWithoutJobSchedule(ScheduleEntity entity) {
        Schedule schedule = new Schedule(
                entity.getId(),
                UsersMapper.toDomain(entity.getUsers()),
                null,
                entity.getStartDatetime(),
                entity.getEndDatetime(),
                entity.getTotalPrice(),
                entity.getStatus(),
                entity.getPaymentStatus(),
                entity.getFeedback()
        );
        return schedule;
    }

    public static Schedule toDomain(ScheduleEntity entity) {
        List<JobSchedule> jobSchedules = null;
        if(entity.getJobSchedules() == null) {
            return toDomainWithoutJobSchedule(entity);
        }
        jobSchedules = entity.getJobSchedules().stream()
                .map(JobScheduleMapper::toDomain)
                .map(js -> {
                    js.setScheduling(toDomainWithoutJobSchedule(entity));
                    return js;
                })
                .toList();

        Schedule schedule = new Schedule(
                entity.getId(),
                UsersMapper.toDomain(entity.getUsers()),
                jobSchedules,
                entity.getStartDatetime(),
                entity.getEndDatetime(),
                entity.getTotalPrice(),
                entity.getStatus(),
                entity.getPaymentStatus(),
                entity.getFeedback()
        );
        return schedule;
    }

    public static CreateScheduleCommand toCreateScheduleCommand(CreateScheduleRequest request, Integer roleId) {
        return new CreateScheduleCommand(
                request.userId(),
                request.jobsIds(),
                request.startDatetime(),
                roleId
        );
    }

    public static ScheduleEntity toEntity(Schedule schedule) {
        ScheduleEntity entity = new ScheduleEntity();
        entity.setId(schedule.getId());
        entity.setUsers(UsersMapper.toEntity(schedule.getUsers()));
        entity.setFeedback(schedule.getFeedback());
        entity.setStatus(schedule.getStatus());
        entity.setPaymentStatus(schedule.getPaymentStatus());
        entity.setTotalPrice(schedule.getTotalPrice());
        entity.setStartDatetime(schedule.getStartDatetime());
        entity.setEndDatetime(schedule.getEndDatetime());
        return entity;
    }

    public static ScheduleResponse toResponse(Schedule schedule) {
        return new ScheduleResponse(
            schedule.getId(),
            schedule.getUsers() != null ? schedule.getUsers().getId() : null,
            schedule.getTotalPrice(),
            schedule.getStartDatetime(),
            schedule.getEndDatetime(),
            schedule.getStatus() != null ? schedule.getStatus().name() : null,
            schedule.getPaymentStatus() != null ? schedule.getPaymentStatus().name() : null,
            schedule.getFeedback()
        );
    }

    public static ScheduleCardResponse toScheduleCardResponse(Schedule schedule) {
        return new ScheduleCardResponse(
                schedule.getId(),
                schedule.getUsers().getUsername().get(),
                schedule.getJobSchedules().stream()
                        .map(js -> js.getJob().getName())
                        .toList(),
                schedule.getStartDatetime(),
                schedule.getEndDatetime(),
                schedule.getTotalPrice(),
                schedule.getPaymentStatus().getDescription(),
                schedule.getStatus().getStatus()
        );
    }

    public static GetAvailableDaysResponse toAvailableDayDto(AvailableDay availableDay) {
        return new GetAvailableDaysResponse(
                availableDay.getDate(),
                availableDay.getWeekDay(),
                availableDay.getAvailable(),
                availableDay.getAvailableTimes()
        );
    }

    public static PatchScheduleCommand toPatchScheduleCommand(SchedulePatchRequest request) {
        return new PatchScheduleCommand(
                request.id(),
                request.feedback(),
                request.status(),
                request.paymentStatus()
        );
    }

    public static void mergePatchCommandInDomain(PatchScheduleCommand command, Schedule schedule) {
        if (command.feedback() != null) {
            schedule.setFeedback(command.feedback());
        }
        if (command.status() != null) {
            schedule.setStatus(command.status());
        }
        if (command.paymentStatus() != null) {
            schedule.setPaymentStatus(command.paymentStatus());
        }
    }

    public static void mergePatchInEntity(ScheduleEntity patch, ScheduleEntity entity) {
        if (patch.getFeedback() != null) {
            entity.setFeedback(patch.getFeedback());
        }
        if (patch.getStatus() != null) {
            entity.setStatus(patch.getStatus());
        }
        if (patch.getPaymentStatus() != null) {
            entity.setPaymentStatus(patch.getPaymentStatus());
        }
    }
}
