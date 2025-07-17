package com.aura8.general_backend.dtos.jobscheduling;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AvailableDayDto {

    private LocalDate date;
    private String weekDay;
    private Boolean isAvailable;
    private List<LocalTime> availableTimes;

    public AvailableDayDto() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public List<LocalTime> getAvailableTimes() {
        return availableTimes;
    }

    public void setAvailableTimes(List<LocalTime> availableTimes) {
        this.availableTimes = availableTimes;
    }
}
