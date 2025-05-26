package com.aura8.general_backend.dtos.schedulingsettings;

import com.aura8.general_backend.entities.SchedulingSettings;
import com.aura8.general_backend.enums.DayOfWeekEnum;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

public class SchedulingSettingsMapper {
    public static SchedulingSettingsPatchRequest toDto(SchedulingSettings schedulingSettings) {
        SchedulingSettingsPatchRequest dto = new SchedulingSettingsPatchRequest();
        dto.setId(1);
        dto.setDaysOfWeek(schedulingSettings.getDaysOfWeek());
        dto.setWorkStart(schedulingSettings.getWorkStart());
        dto.setWorkEnd(schedulingSettings.getWorkEnd());
        dto.setBreakStart(schedulingSettings.getBreakStart());
        dto.setBreakEnd(schedulingSettings.getBreakEnd());
        return dto;
    }

    public static SchedulingSettings toEntity(SchedulingSettingsPatchRequest dto) {
        SchedulingSettings schedulingSettings = new SchedulingSettings();
        schedulingSettings.setId(1);
        schedulingSettings.setWorkStart(dto.getWorkStart());
        schedulingSettings.setWorkEnd(dto.getWorkEnd());
        schedulingSettings.setBreakStart(dto.getBreakStart());
        schedulingSettings.setBreakEnd(dto.getBreakEnd());
        try {
            schedulingSettings.setDaysOfWeek(dto.getDaysOfWeek().stream()
                    .map(DayOfWeekEnum::valueOf)
                    .map(DayOfWeekEnum::name)
                    .toList());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(400, "Dias da semana inválidos", e);
        } catch (NullPointerException e) {
            schedulingSettings.setDaysOfWeek(null);
        } catch (Exception e) {
            throw new ResponseStatusException(500, "Erro inesperado", e);
        }
        return schedulingSettings;
    }
}
