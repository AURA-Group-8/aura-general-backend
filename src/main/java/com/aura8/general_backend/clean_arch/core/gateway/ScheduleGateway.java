package com.aura8.general_backend.clean_arch.core.gateway;

import com.aura8.general_backend.clean_arch.core.domain.Schedule;
import com.aura8.general_backend.clean_arch.core.domain.valueobject.PageElement;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleGateway {
    Schedule create(Schedule schedule);
    Schedule findById(Integer id);
    void deleteById(Integer id);
    Schedule patch(Schedule schedule);
    List<Schedule> findAll(Integer page, Integer size, String sortBy, String direction);
    PageElement<Schedule> findAllPageable(Integer page, Integer size, String sortBy, String direction);
    List<Schedule> findByStartDateBetween(LocalDateTime startDate, LocalDateTime endDate, boolean getCanceled);
    List<Schedule> findByUsersId(Integer userId);
}
