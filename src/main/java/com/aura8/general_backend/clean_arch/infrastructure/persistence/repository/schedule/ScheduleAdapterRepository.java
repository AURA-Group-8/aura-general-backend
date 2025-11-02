package com.aura8.general_backend.clean_arch.infrastructure.persistence.repository.schedule;

import com.aura8.general_backend.clean_arch.core.domain.Schedule;
import com.aura8.general_backend.clean_arch.core.domain.enums.ScheduleStatus;
import com.aura8.general_backend.clean_arch.core.gateway.ScheduleGateway;
import com.aura8.general_backend.clean_arch.infrastructure.mapper.ScheduleMapper;
import com.aura8.general_backend.clean_arch.infrastructure.persistence.entity.ScheduleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ScheduleAdapterRepository implements ScheduleGateway {
    private final ScheduleJpaRepository repository;

    public ScheduleAdapterRepository(ScheduleJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Schedule create(Schedule schedule) {
        ScheduleEntity entity = ScheduleMapper.toEntity(schedule);
        ScheduleEntity savedEntity = repository.save(entity);
        return ScheduleMapper.toDomain(savedEntity);
    }

    @Override
    public Schedule findById(Integer id) {
        ScheduleEntity entity = repository.findByIdAndCanceled(id, false).orElse(null);
        if (entity == null) return null;
        return ScheduleMapper.toDomain(entity);
    }

    @Override
    public void deleteById(Integer id) {
        ScheduleEntity entity = repository.findById(id).orElse(null);
        if (entity != null) {
            entity.setCanceled(true);
            entity.setStatus(ScheduleStatus.CANCELADO);
            repository.save(entity);
        }
    }

    @Override
    public Schedule patch(Schedule patchSchedule) {
        ScheduleEntity entity = repository.findByIdAndCanceled(patchSchedule.getId(), false)
                .orElse(null);
        if (entity == null) {
            throw new RuntimeException("Schedule de id: %d nao encontrado".formatted(patchSchedule.getId()));
        }
        ScheduleEntity patchScheduleEntity = ScheduleMapper.toEntity(patchSchedule);
        ScheduleMapper.mergePatchInEntity(patchScheduleEntity, entity);
        ScheduleEntity updatedEntity = repository.save(entity);
        return ScheduleMapper.toDomain(updatedEntity);
    }

    @Override
    public List<Schedule> findAll(Integer page, Integer size, String sortBy, String direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        Page<ScheduleEntity> entityPage = repository.findAllByCanceled(pageable, false);
        List<Schedule> schedules = entityPage.getContent().stream()
                .map(ScheduleMapper::toDomain)
                .toList();
        return schedules;
    }

    @Override
    public List<Schedule> findByStartDateBetween(LocalDateTime startDate, LocalDateTime endDate, boolean canceled) {
        List<ScheduleEntity> scheduleEntities = repository.findByStartDatetimeBetweenAndCanceled(startDate, endDate, canceled);
        return scheduleEntities.stream()
            .map(ScheduleMapper::toDomain)
            .toList();
    }

    @Override
    public List<Schedule> findByUsersId(Integer userId) {
        return repository.findByUsersIdAndCanceled(userId, false).stream()
                .map(ScheduleMapper::toDomain)
                .toList();
    }
}
