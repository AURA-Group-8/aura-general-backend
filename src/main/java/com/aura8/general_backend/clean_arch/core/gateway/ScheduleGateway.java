package com.aura8.general_backend.clean_arch.core.gateway;

import com.aura8.general_backend.clean_arch.core.domain.Schedule;

import java.util.List;

public interface ScheduleGateway {
    Schedule create(Schedule schedule);
    Schedule findById(Integer id);
    void deleteById(Integer id);
    Schedule update(Schedule schedule);
    List<Schedule> findAll(Integer userId);
}
