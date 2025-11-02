package com.aura8.general_backend.clean_arch.application.usecase.availableday;

import com.aura8.general_backend.clean_arch.core.domain.AvailableDay;
import com.aura8.general_backend.clean_arch.core.domain.ScheduleSetting;
import com.aura8.general_backend.clean_arch.core.gateway.ScheduleGateway;
import com.aura8.general_backend.clean_arch.core.gateway.ScheduleSettingGateway;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class GetAvailiblesDaysUseCase {
    private final ScheduleSettingGateway scheduleSettingGateway;
    private final ScheduleGateway scheduleGateway;

    public GetAvailiblesDaysUseCase(ScheduleSettingGateway scheduleSettingGateway, ScheduleGateway scheduleGateway) {
        this.scheduleSettingGateway = scheduleSettingGateway;
        this.scheduleGateway = scheduleGateway;
    }

    public List<AvailableDay> getAvailablesDays(Integer durationInMinutes, LocalDate firstDayOfWeek) {
        ScheduleSetting scheduleSetting = scheduleSettingGateway.findById(1);
        Integer defaultSlotDuration = 30;
        if(scheduleSetting == null) {
            throw new IllegalArgumentException("Configuração de agenda não encontrada.");
        }
        firstDayOfWeek = firstDayOfWeek.with(DayOfWeek.MONDAY);
        firstDayOfWeek = firstDayOfWeek.minusDays(1); // Ajusta para domingo
        List<AvailableDay> availableDays = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate currentDate = firstDayOfWeek.plusDays(i);
            AvailableDay availableDay = new AvailableDay(currentDate);
            availableDay.setAvailable(true);
            if(!scheduleSetting.getDaysOfWeek().contains(currentDate.getDayOfWeek())) {
                availableDay.setAvailable(false);
                availableDays.add(availableDay);
                continue;
            }

            availableDay.generateIntervalOfAvailableTimes(
                    defaultSlotDuration,
                    scheduleSetting.getWorkStart(),
                    scheduleSetting.getBreakStart()
            );
            availableDay.generateIntervalOfAvailableTimes(
                    defaultSlotDuration,
                    scheduleSetting.getBreakEnd(),
                    scheduleSetting.getWorkEnd()
            );

            availableDay.removeTimesSlots(durationInMinutes, scheduleSetting);

            availableDay.removeTimesSlotsIfDontFitInDay(durationInMinutes);

            scheduleGateway.findByStartDateBetween(
                    currentDate.atStartOfDay(),
                    currentDate.plusDays(1).atStartOfDay(),
                    false
            ).forEach(schedule -> {
                availableDay.removeTimesSlots(durationInMinutes, schedule);
            });



            if(availableDay.getAvailableTimes().isEmpty()) {
                availableDay.setAvailable(false);
            }

            availableDays.add(availableDay);
        }

        return availableDays;
    }
}
