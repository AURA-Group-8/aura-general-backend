package com.aura8.general_backend.clean_arch.application.usecase.schedulesettings.patch;

import java.time.LocalTime;
import java.util.List;

public record PatchScheduleSettingCommand(
        List<String> daysOfWeek,
        LocalTime workStart,
        LocalTime workEnd,
        LocalTime breakStart,
        LocalTime breakEnd
) {
}
