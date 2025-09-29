package com.aura8.general_backend.clean_arch.infraestructure.mapper;

import com.aura8.general_backend.clean_arch.application.usecase.schedulesettings.patch.PatchScheduleSettingCommand;
import com.aura8.general_backend.clean_arch.core.domain.ScheduleSetting;
import com.aura8.general_backend.clean_arch.infraestructure.dto.schedulesetting.FindScheduleSettingResponse;
import com.aura8.general_backend.clean_arch.infraestructure.dto.schedulesetting.PatchScheduleSettingRequest;
import com.aura8.general_backend.clean_arch.infraestructure.dto.schedulesetting.PatchScheduleSettingResponse;
import com.aura8.general_backend.clean_arch.infraestructure.persistence.entity.ScheduleSettingEntity;

import java.time.DayOfWeek;
import java.util.List;

public class ScheduleSettingMapper {
    public static FindScheduleSettingResponse toFindResponse(ScheduleSetting scheduleSetting) {
        List<String> daysOfWeek = mapDaysOfWeekToString(scheduleSetting.getDaysOfWeek());
        return new FindScheduleSettingResponse(
                daysOfWeek,
                scheduleSetting.getWorkStart(),
                scheduleSetting.getWorkEnd(),
                scheduleSetting.getBreakStart(),
                scheduleSetting.getBreakEnd()
        );
    }

    public static ScheduleSetting toDomain(ScheduleSettingEntity scheduleSettingEntity) {
        List<DayOfWeek> daysOfWeek = mapDaysOfWeek(scheduleSettingEntity.getDaysOfWeek());
        return new ScheduleSetting(
                daysOfWeek,
                scheduleSettingEntity.getWorkStart(),
                scheduleSettingEntity.getWorkEnd(),
                scheduleSettingEntity.getBreakStart(),
                scheduleSettingEntity.getBreakEnd()
        );
    }

    public static PatchScheduleSettingCommand toPatchScheduleSettingCommand(PatchScheduleSettingRequest request) {
        return new PatchScheduleSettingCommand(
                request.daysOfWeek(),
                request.workStart(),
                request.workEnd(),
                request.breakStart(),
                request.breakEnd()
        );
    }

    public static PatchScheduleSettingResponse toPatchScheduleSettingResponse(ScheduleSetting scheduleSetting){
        List<String> daysOfWeek = mapDaysOfWeekToString(scheduleSetting.getDaysOfWeek());
        return new PatchScheduleSettingResponse(
                daysOfWeek,
                scheduleSetting.getWorkStart(),
                scheduleSetting.getWorkEnd(),
                scheduleSetting.getBreakStart(),
                scheduleSetting.getBreakEnd()
        );
    }

    public static void merge(ScheduleSetting scheduleSetting, PatchScheduleSettingCommand command) {
        if (command.daysOfWeek() != null) {
            List<DayOfWeek> daysOfWeek = mapDaysOfWeek(command.daysOfWeek());
            scheduleSetting.setDaysOfWeek(daysOfWeek);
        }
        if (command.workStart() != null) {
            scheduleSetting.setWorkStart(command.workStart());
        }
        if (command.workEnd() != null) {
            scheduleSetting.setWorkEnd(command.workEnd());
        }
        if (command.breakStart() != null) {
            scheduleSetting.setBreakStart(command.breakStart());
        }
        if (command.breakEnd() != null) {
            scheduleSetting.setBreakEnd(command.breakEnd());
        }
    }

    public static void merge(ScheduleSettingEntity entity, ScheduleSetting scheduleSetting) {
        if (scheduleSetting.getDaysOfWeek() != null) {
            List<String> daysOfWeek = mapDaysOfWeekToString(scheduleSetting.getDaysOfWeek());
            entity.setDaysOfWeek(daysOfWeek);
        }
        if (scheduleSetting.getWorkStart() != null) {
            entity.setWorkStart(scheduleSetting.getWorkStart());
        }
        if (scheduleSetting.getWorkEnd() != null) {
            entity.setWorkEnd(scheduleSetting.getWorkEnd());
        }
        if (scheduleSetting.getBreakStart() != null) {
            entity.setBreakStart(scheduleSetting.getBreakStart());
        }
        if (scheduleSetting.getBreakEnd() != null) {
            entity.setBreakEnd(scheduleSetting.getBreakEnd());
        }
    }

    public static List<DayOfWeek> mapDaysOfWeek(List<String> daysOfWeek) {
        return daysOfWeek.stream().map(
                day -> switch (day) {
                    case "SEGUNDA" -> DayOfWeek.MONDAY;
                    case "TERCA" -> DayOfWeek.TUESDAY;
                    case "QUARTA" -> DayOfWeek.WEDNESDAY;
                    case "QUINTA" -> DayOfWeek.THURSDAY;
                    case "SEXTA" -> DayOfWeek.FRIDAY;
                    case "SABADO" -> DayOfWeek.SATURDAY;
                    case "DOMINGO" -> DayOfWeek.SUNDAY;
                    default -> throw new IllegalArgumentException("Dia da semana inválido: " + day);
                }
        ).toList();
    }

    public static List<String> mapDaysOfWeekToString(List<DayOfWeek> daysOfWeek) {
        return daysOfWeek.stream().map(
                day -> switch (day) {
                    case MONDAY -> "SEGUNDA";
                    case TUESDAY -> "TERCA";
                    case WEDNESDAY -> "QUARTA";
                    case THURSDAY -> "QUINTA";
                    case FRIDAY -> "SEXTA";
                    case SATURDAY -> "SABADO";
                    case SUNDAY -> "DOMINGO";
                }
        ).toList();
    }
}