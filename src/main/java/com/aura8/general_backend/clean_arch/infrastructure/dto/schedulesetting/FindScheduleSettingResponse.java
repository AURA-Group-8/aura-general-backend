package com.aura8.general_backend.clean_arch.infrastructure.dto.schedulesetting;

import java.time.LocalTime;
import java.util.List;

public record FindScheduleSettingResponse (
        List<String> daysOfWeek,
        LocalTime workStart,
        LocalTime workEnd,
        LocalTime breakStart,
        LocalTime breakEnd
) {
}
