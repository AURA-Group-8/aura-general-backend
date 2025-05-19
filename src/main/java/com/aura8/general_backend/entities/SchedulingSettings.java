package com.aura8.general_backend.entities;

import com.aura8.general_backend.enums.DayOfWeekEnum;
import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.List;

@Entity
public class SchedulingSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private List<DayOfWeekEnum> daysOfWeek;

    private LocalTime workStart;
    private LocalTime workEnd;
    private LocalTime breakStart;
    private LocalTime breakEnd;
}
