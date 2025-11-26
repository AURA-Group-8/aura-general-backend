package com.aura8.general_backend.clean_arch.application.usecase.schedule.find.findall;

import com.aura8.general_backend.clean_arch.core.domain.Schedule;
import com.aura8.general_backend.clean_arch.core.domain.valueobject.PageElement;
import com.aura8.general_backend.clean_arch.core.gateway.ScheduleGateway;
import com.aura8.general_backend.clean_arch.infrastructure.mapper.ScheduleMapper;
import com.aura8.general_backend.clean_arch.infrastructure.persistence.entity.ScheduleEntity;
import com.aura8.general_backend.clean_arch.infrastructure.persistence.repository.schedule.ScheduleJpaRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllScheduleUseCase {
    private final ScheduleGateway repository;

    public FindAllScheduleUseCase(ScheduleGateway repository) {
        this.repository = repository;
    }

    public Page<Schedule> findAll(int page, int size, String sortBy, String direction) {
        List<Schedule> entityPage = repository.findAll(page, size, sortBy, direction);
        Page<Schedule> schedulePage = new PageImpl<>(
                entityPage,
                Pageable.ofSize(size).withPage(page),
                entityPage.size()
        );
        return schedulePage;
    }

    public PageElement<Schedule> findAllPageable(int page, int size, String sortBy, String direction) {
        PageElement<Schedule> entityPage = repository.findAllPageable(page, size, sortBy, direction);
        return entityPage;
    }
}

