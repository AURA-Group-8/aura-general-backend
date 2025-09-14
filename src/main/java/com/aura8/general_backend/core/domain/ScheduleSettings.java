package com.aura8.general_backend.core.domain;

import java.time.LocalTime;
import java.util.List;

public class ScheduleSettings {
    private List<String> daysOfWeek;
    private LocalTime workStart;
    private LocalTime workEnd;
    private LocalTime breakStart;
    private LocalTime breakEnd;

    private ScheduleSettings(List<String> daysOfWeek, LocalTime workStart, LocalTime workEnd, LocalTime breakStart, LocalTime breakEnd) {
        this.daysOfWeek = daysOfWeek;
        this.workStart = workStart;
        this.workEnd = workEnd;
        this.breakStart = breakStart;
        this.breakEnd = breakEnd;
    }
}
