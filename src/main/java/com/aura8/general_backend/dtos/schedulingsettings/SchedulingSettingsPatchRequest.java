package com.aura8.general_backend.dtos.schedulingsettings;

import java.time.LocalTime;
import java.util.List;

public class SchedulingSettingsPatchRequest {
    private Integer id;
    private LocalTime workStart;
    private LocalTime workEnd;
    private LocalTime breakStart;
    private LocalTime breakEnd;
    private List<String> daysOfWeek;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalTime getWorkStart() {
        return workStart;
    }

    public void setWorkStart(LocalTime workStart) {
        this.workStart = workStart;
    }

    public LocalTime getWorkEnd() {
        return workEnd;
    }

    public void setWorkEnd(LocalTime workEnd) {
        this.workEnd = workEnd;
    }

    public LocalTime getBreakStart() {
        return breakStart;
    }

    public void setBreakStart(LocalTime breakStart) {
        this.breakStart = breakStart;
    }

    public LocalTime getBreakEnd() {
        return breakEnd;
    }

    public void setBreakEnd(LocalTime breakEnd) {
        this.breakEnd = breakEnd;
    }

    public List<String> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(List<String> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }
}
