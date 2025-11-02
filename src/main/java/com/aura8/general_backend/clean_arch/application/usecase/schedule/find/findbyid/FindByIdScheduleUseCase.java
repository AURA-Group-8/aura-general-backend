package com.aura8.general_backend.clean_arch.application.usecase.schedule.find.findbyid;

import com.aura8.general_backend.clean_arch.application.exception.ElementNotFoundException;
import com.aura8.general_backend.clean_arch.core.domain.Schedule;
import com.aura8.general_backend.clean_arch.core.gateway.ScheduleGateway;
import org.springframework.stereotype.Service;

@Service
public class FindByIdScheduleUseCase {
    private final ScheduleGateway scheduleGateway;

    public FindByIdScheduleUseCase(ScheduleGateway scheduleGateway) {
        this.scheduleGateway = scheduleGateway;
    }

    public Schedule findById(Integer id) {
        Schedule schedule = scheduleGateway.findById(id);
        if (schedule == null) {
            throw new ElementNotFoundException("Agendamento de id: %d não encontrado".formatted(id));
        }
        return schedule;
    }
}

