package com.aura8.general_backend.service;

import com.aura8.general_backend.entities.SchedulingSettings;
import com.aura8.general_backend.exception.ElementNotFoundException;
import com.aura8.general_backend.repository.SchedulingSettingsRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Service
public class SchedulingSettingsService {


    private final SchedulingSettingsRepository schedulingSettingsRepository;

    public SchedulingSettingsService(SchedulingSettingsRepository schedulingSettingsRepository) {
        this.schedulingSettingsRepository = schedulingSettingsRepository;
    }

    public SchedulingSettings getSchedulingSettings() {
        return schedulingSettingsRepository.findById(1)
                .orElseThrow(() -> new ElementNotFoundException("Configuração não encontrada"));
    }

    public SchedulingSettings updatePatch(SchedulingSettings schedulingSettings){
        Optional<SchedulingSettings> settingsOptional = schedulingSettingsRepository.findById(schedulingSettings.getId());
        settingsOptional.orElseThrow(
                () -> new ElementNotFoundException("Configaração de id: %d não encontrada".formatted(schedulingSettings.getId()))
        );
        SchedulingSettings currentSettings = settingsOptional.get();

        if(schedulingSettings.getWorkStart() == null) {
            schedulingSettings.setWorkStart(currentSettings.getWorkStart());
        }
        if (schedulingSettings.getWorkEnd() == null){
            schedulingSettings.setWorkEnd(currentSettings.getWorkEnd());
        }
        if(schedulingSettings.getBreakStart() == null) {
            schedulingSettings.setBreakStart(currentSettings.getBreakStart());
        }
        if (schedulingSettings.getBreakEnd() == null){
            schedulingSettings.setBreakEnd(currentSettings.getBreakEnd());
        }
        if(schedulingSettings.getDaysOfWeek() == null) {
            schedulingSettings.setDaysOfWeek(currentSettings.getDaysOfWeek());
        }

        schedulingSettingsRepository.save(schedulingSettings);
        return schedulingSettings;
    }


}
