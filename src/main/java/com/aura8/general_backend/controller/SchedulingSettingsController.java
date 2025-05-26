package com.aura8.general_backend.controller;

import com.aura8.general_backend.dtos.schedulingsettings.SchedulingSettingsMapper;
import com.aura8.general_backend.dtos.schedulingsettings.SchedulingSettingsPatchRequest;
import com.aura8.general_backend.entities.SchedulingSettings;
import com.aura8.general_backend.service.SchedulingSettingsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/configuracao-agendamento")
public class SchedulingSettingsController {

    private final SchedulingSettingsService schedulingSettingsService;

    public SchedulingSettingsController(SchedulingSettingsService schedulingSettingsService) {
        this.schedulingSettingsService = schedulingSettingsService;
    }

    @GetMapping
    public ResponseEntity<SchedulingSettings> get() {
        return ResponseEntity.ok().body(schedulingSettingsService.getSchedulingSettings());
    }

    @PatchMapping
    public ResponseEntity<SchedulingSettings> patch(@RequestBody SchedulingSettingsPatchRequest schedulingSettingsPatchRequest) {
        SchedulingSettings entity = SchedulingSettingsMapper.toEntity(schedulingSettingsPatchRequest);
        SchedulingSettings updatePatch = schedulingSettingsService.updatePatch(entity);
        return ResponseEntity.ok().body(updatePatch);
    }
}
