package com.aura8.general_backend.controller;

import com.aura8.general_backend.dtos.schedulingsettings.SchedulingSettingsMapper;
import com.aura8.general_backend.dtos.schedulingsettings.SchedulingSettingsPatchRequest;
import com.aura8.general_backend.entities.SchedulingSettings;
import com.aura8.general_backend.service.SchedulingSettingsService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Confirações de agendamento", description = "Endpoint para gerenciar as configurações de agendamento")
@RequestMapping("/configuracao-agendamento")
public class SchedulingSettingsController {

    private final SchedulingSettingsService schedulingSettingsService;

    public SchedulingSettingsController(SchedulingSettingsService schedulingSettingsService) {
        this.schedulingSettingsService = schedulingSettingsService;
    }

    @CrossOrigin(origins = "*")
    @SecurityRequirement(name = "Bearer")
    @ApiResponse(responseCode = "200", description = "Configuração de agendamento padrão retornada com sucesso")
    @ApiResponse(responseCode = "404", description = "Configuração de agendamento padrão não encontrada")
    @GetMapping
    public ResponseEntity<SchedulingSettings> get() {
        return ResponseEntity.ok().body(schedulingSettingsService.getSchedulingSettings());
    }

    @CrossOrigin(origins = "*")
    @SecurityRequirement(name = "Bearer")
    @ApiResponse(responseCode = "200", description = "Configuração de agendamento atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Configuração de agendamento padrão não encontrada")
    @PatchMapping
    public ResponseEntity<SchedulingSettings> patch(@RequestBody SchedulingSettingsPatchRequest schedulingSettingsPatchRequest) {
        SchedulingSettings entity = SchedulingSettingsMapper.toEntity(schedulingSettingsPatchRequest);
        SchedulingSettings updatePatch = schedulingSettingsService.updatePatch(entity);
        return ResponseEntity.ok().body(updatePatch);
    }
}
