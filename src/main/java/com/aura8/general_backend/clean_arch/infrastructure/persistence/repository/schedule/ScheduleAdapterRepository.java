package com.aura8.general_backend.clean_arch.infrastructure.persistence.repository.schedule;

import com.aura8.general_backend.clean_arch.core.domain.Schedule;
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
        ScheduleEntity entity = repository.findByIdAndCanceledFalse(id).orElse(null);
        if (entity == null) return null;
        return ScheduleMapper.toDomain(entity);
    }

    @Override
    public void deleteById(Integer id) {
        ScheduleEntity entity = repository.findById(id).orElse(null);
        if (entity != null) {
            entity.setCanceled(true);
            repository.save(entity);
        }
    }

    @Override
    public Schedule patch(Schedule schedule) {
        return null;
    }

    @Override
    public List<Schedule> findAll(Integer page, Integer size, String sortBy, String direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        Page<ScheduleEntity> entityPage = repository.findAllByCanceledFalse(pageable);
        List<Schedule> schedules = entityPage.getContent().stream()
                .map(ScheduleMapper::toDomain)
                .toList();
        return schedules;
    }

    @Override
    public List<Schedule> findByStartDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        List<ScheduleEntity> scheduleEntities = repository.findByStartDatetimeBetweenAndCanceledFalse(startDate, endDate);
        return scheduleEntities.stream()
            .map(ScheduleMapper::toDomain)
            .toList();
    }
}
