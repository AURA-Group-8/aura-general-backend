package com.aura8.general_backend.dtos.schedulingsettings;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalTime;
import java.util.List;

public class SchedulingSettingsPatchRequest {
    private Integer id;
    @Schema(description = "Horário de inicio", example = "08:00:00")
    private LocalTime workStart;
    @Schema(description = "Horário de fim", example = "17:00:00")
    private LocalTime workEnd;
    @Schema(description = "Horário de início do intervalo", example = "12:00:00")
    private LocalTime breakStart;
    @Schema(description = "Horário de fim do intervalo", example = "13:00:00")
    private LocalTime breakEnd;
    @Schema(description = "Dias da semana em que o horário de trabalho se aplica", example = "[\"SEGUNDA\", \"TERCA\", \"QUARTA\"]")
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
