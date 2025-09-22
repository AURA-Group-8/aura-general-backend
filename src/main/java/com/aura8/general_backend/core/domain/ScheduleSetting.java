package com.aura8.general_backend.core.domain;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public class ScheduleSetting {
    private List<DayOfWeek> daysOfWeek;
    private LocalTime workStart;
    private LocalTime workEnd;
    private LocalTime breakStart;
    private LocalTime breakEnd;

    public ScheduleSetting(List<DayOfWeek> daysOfWeek, LocalTime workStart, LocalTime workEnd, LocalTime breakStart, LocalTime breakEnd) {
        this.daysOfWeek = daysOfWeek;

        this.workStart = validateTime(workStart, "workStart");
        this.breakStart = validateTime(breakStart, "breakStart");
        this.workEnd = validateTime(workEnd, "workEnd");
        this.breakEnd = validateTime(breakEnd, "breakEnd");
    }

    public LocalTime validateTime(LocalTime time, String typeOfValidation) {
        if (time == null) {
            throw new IllegalArgumentException("Horário não pode ser nulo.");
        }

        int validations = switch (typeOfValidation) {
            case "workStart" -> 1;
            case "breakStart" -> 2;
            case "workEnd" -> 3;
            default -> throw new IllegalArgumentException("Tipo de validação inválido.");
        };

        switch (validations) {
            case 1:
                if (time.isAfter(breakStart) || time.equals(breakStart)) {
                    throw new IllegalArgumentException("Horário de %s não pode ser após o horário de início do intervalo.".formatted(typeOfValidation));
                }
            case 2:
                if (time.isAfter(breakEnd) || time.equals(breakEnd)) {
                    throw new IllegalArgumentException("Horário de %s não pode ser após o horário de término do intervalo.".formatted(typeOfValidation));
                }
            case 3:
                if (time.isAfter(workEnd) || time.equals(workEnd)) {
                    throw new IllegalArgumentException("Horário de de %s não pode ser após o horário de término.".formatted(typeOfValidation));
                }
        }
        return time;
    }

    public List<DayOfWeek> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(List<DayOfWeek> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public LocalTime getWorkStart() {
        return workStart;
    }

    public void setWorkStart(LocalTime workStart) {
        this.workStart = validateTime(workStart, "workStart");
    }

    public LocalTime getWorkEnd() {
        return workEnd;
    }

    public void setWorkEnd(LocalTime workEnd) {
        this.workEnd = validateTime(workEnd, "workEnd");
    }

    public LocalTime getBreakStart() {
        return breakStart;
    }

    public void setBreakStart(LocalTime breakStart) {
        this.breakStart = validateTime(breakStart, "breakStart");
    }

    public LocalTime getBreakEnd() {
        return breakEnd;
    }

    public void setBreakEnd(LocalTime breakEnd) {
        this.breakEnd = validateTime(breakEnd, "breakEnd");
    }
}
