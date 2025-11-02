package com.aura8.general_backend.clean_arch.infrastructure.dto.schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record GetAvailableDaysResponse(
        LocalDate date,
        String weekDay,
        Boolean isAvailable,
        List<LocalTime> availableTimes
) {
}
