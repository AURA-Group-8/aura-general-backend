package com.aura8.general_backend.clean_arch.application.usecase.schedule.find.findbyuserid;

import com.aura8.general_backend.clean_arch.core.domain.Schedule;
import com.aura8.general_backend.clean_arch.core.domain.valueobject.PageElement;
import com.aura8.general_backend.clean_arch.core.gateway.ScheduleGateway;
import org.springframework.stereotype.Service;

@Service
public class FindByUserIdScheduleUseCase {

    private final ScheduleGateway scheduleGateway;

    public FindByUserIdScheduleUseCase(ScheduleGateway scheduleGateway) {
        this.scheduleGateway = scheduleGateway;
    }

    public PageElement<Schedule> findByUserIdPageable(Integer page, Integer size, String sortBy, String direction, Integer userId) {
        PageElement<Schedule> entityPage = scheduleGateway.findByUserIdPageable(page, size, sortBy, direction, userId);
        return entityPage;
    }
}
