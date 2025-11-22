package com.aura8.general_backend.clean_arch.infrastructure.controller;

import com.aura8.general_backend.clean_arch.application.usecase.schedulesettings.find.FindPrincipalScheduleSettingUseCase;
import com.aura8.general_backend.clean_arch.application.usecase.schedulesettings.patch.PatchScheduleSettingCommand;
import com.aura8.general_backend.clean_arch.application.usecase.schedulesettings.patch.PatchScheduleSettingUseCase;
import com.aura8.general_backend.clean_arch.core.domain.ScheduleSetting;
import com.aura8.general_backend.clean_arch.infrastructure.dto.schedulesetting.FindScheduleSettingResponse;
import com.aura8.general_backend.clean_arch.infrastructure.dto.schedulesetting.PatchScheduleSettingRequest;
import com.aura8.general_backend.clean_arch.infrastructure.dto.schedulesetting.PatchScheduleSettingResponse;
import com.aura8.general_backend.clean_arch.infrastructure.mapper.ScheduleSettingMapper;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Confirações de agendamento", description = "Endpoint para gerenciar as configurações de agendamento")
@RequestMapping("api/configuracao-agendamento")
public class ScheduleSettingController {

    private final FindPrincipalScheduleSettingUseCase findPrincipalScheduleSettingUseCase;
    private final PatchScheduleSettingUseCase patchScheduleSettingUseCase;

    public ScheduleSettingController(FindPrincipalScheduleSettingUseCase findPrincipalScheduleSettingUseCase, PatchScheduleSettingUseCase patchScheduleSettingUseCase) {
        this.findPrincipalScheduleSettingUseCase = findPrincipalScheduleSettingUseCase;
        this.patchScheduleSettingUseCase = patchScheduleSettingUseCase;
    }

    @CrossOrigin(origins = "*")
    @SecurityRequirement(name = "Bearer")
    @ApiResponse(responseCode = "200", description = "Configuração de agendamento padrão retornada com sucesso")
    @ApiResponse(responseCode = "404", description = "Configuração de agendamento padrão não encontrada")
    @GetMapping
    public ResponseEntity<FindScheduleSettingResponse> get() {
        ScheduleSetting scheduleSetting = findPrincipalScheduleSettingUseCase.find();
        FindScheduleSettingResponse scheduleSettingResponse = ScheduleSettingMapper.toFindResponse(scheduleSetting);
        return ResponseEntity.ok(scheduleSettingResponse);
    }

    @CrossOrigin(origins = "*")
    @SecurityRequirement(name = "Bearer")
    @ApiResponse(responseCode = "200", description = "Configuração de agendamento atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Configuração de agendamento padrão não encontrada")
    @PatchMapping
    public ResponseEntity<PatchScheduleSettingResponse> patch(@RequestBody PatchScheduleSettingRequest schedulingSettingsPatchRequest) {
        PatchScheduleSettingCommand patchScheduleSettingCommand = ScheduleSettingMapper.toPatchScheduleSettingCommand(schedulingSettingsPatchRequest);
        ScheduleSetting scheduleSetting = patchScheduleSettingUseCase.patch(patchScheduleSettingCommand);
        PatchScheduleSettingResponse patchScheduleSettingResponse = ScheduleSettingMapper.toPatchScheduleSettingResponse(scheduleSetting);
        return ResponseEntity.ok(patchScheduleSettingResponse);
    }

}
