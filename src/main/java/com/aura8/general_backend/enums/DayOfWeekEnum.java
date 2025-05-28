package com.aura8.general_backend.enums;

import java.time.DayOfWeek;

public enum DayOfWeekEnum {
    SEGUNDA("MONDAY", 1),
    TERCA("TUESDAY", 2),
    QUARTA("WEDNESDAY", 3),
    QUINTA("THURSDAY", 4),
    SEXTA("FRIDAY", 5),
    SABADO("SATURDAY", 6),
    DOMINGO("SUNDAY", 7)
    ;

    private final String name;
    private final Integer number;

    DayOfWeekEnum(String name, Integer number) {
        this.name = name;
        this.number = number;
    }

    public DayOfWeek getDayOfWeek() {
        return DayOfWeek.valueOf(name);
    }

    public static String getNameByNumber(Integer number) {
        for (DayOfWeekEnum day : DayOfWeekEnum.values()) {
            if (day.number.equals(number)) {
                return day.name();
            }
        }
        return null;
    }

}
