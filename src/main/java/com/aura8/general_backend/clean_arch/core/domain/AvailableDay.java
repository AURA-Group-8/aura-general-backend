package com.aura8.general_backend.clean_arch.core.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AvailableDay {
    private final String weekDay;
    private final LocalDate date;
    private List<LocalTime> availableTimes = new ArrayList<>();
    private Boolean isAvailable = true;

    public AvailableDay(LocalDate date) {
        this.weekDay = translateDayOfWeek(date.getDayOfWeek().toString());
        this.date = date;
    }

    private String translateDayOfWeek(String dayOfWeek) {
        return switch (dayOfWeek) {
            case "MONDAY" -> "SEGUNDA";
            case "TUESDAY" -> "TERCA";
            case "WEDNESDAY" -> "QUARTA";
            case "THURSDAY" -> "QUINTA";
            case "FRIDAY" -> "SEXTA";
            case "SATURDAY" -> "SABADO";
            case "SUNDAY" -> "DOMINGO";
            default -> dayOfWeek;
        };
    }

    public void generateIntervalOfAvailableTimes(Integer minutesPerTimeSlot, LocalTime intervalStart, LocalTime intervalEnd) {
        List<LocalTime> times = this.availableTimes;
        LocalTime timeSlot = intervalStart;
        LocalTime nextTimeSlot = timeSlot.plusMinutes(minutesPerTimeSlot);

        while (nextTimeSlot.isBefore(intervalEnd)) {
            times.add(timeSlot);
            timeSlot = nextTimeSlot;
            nextTimeSlot = timeSlot.plusMinutes(minutesPerTimeSlot);
        }
        if(nextTimeSlot.equals(intervalEnd)) {
            times.add(timeSlot);
        }
        this.availableTimes = times;
    }

    public void removeTimesSlots(Integer durationInMinutes, ScheduleSetting scheduleSetting) {
        this.availableTimes.removeIf(time -> {
                    LocalTime timeEnd = time.plusMinutes(durationInMinutes);
                    return scheduleSetting.isTimeInUnavailableIntervals(timeEnd);
                }
        );
    }

    public void removeTimesSlots(Integer durationInMinutes, Schedule schedule) {
        this.availableTimes.removeIf(time -> {
                    LocalDateTime dateTime = date.atTime(time);
                    return schedule.isTimeColliding(dateTime, durationInMinutes);
                }
        );
    }

    public void removeTimesSlotsIfDontFitInDay(Integer durationInMinutes) {
        this.availableTimes.removeIf(time -> {
            LocalDateTime dateTime = date.atTime(time);
            LocalDateTime endDateTime = dateTime.plusMinutes(durationInMinutes);
            return !endDateTime.toLocalDate().equals(date);
        });
    }

    public LocalDate getDate() {
        return date;
    }

    public List<LocalTime> getAvailableTimes() {
        return availableTimes;
    }

    public void setAvailableTimes(List<LocalTime> availableTimes) {
        this.availableTimes = availableTimes;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }
}
