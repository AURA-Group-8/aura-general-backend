package com.aura8.general_backend.clean_arch.infrastructure.controller;

import com.aura8.general_backend.clean_arch.application.usecase.availableday.GetAvailiblesDaysUseCase;
import com.aura8.general_backend.clean_arch.application.usecase.schedule.create.CreateScheduleCommand;
import com.aura8.general_backend.clean_arch.application.usecase.schedule.create.CreateScheduleUseCase;
import com.aura8.general_backend.clean_arch.application.usecase.schedule.delete.DeleteScheduleUseCase;
import com.aura8.general_backend.clean_arch.application.usecase.schedule.find.findbyid.FindByIdScheduleUseCase;
import com.aura8.general_backend.clean_arch.application.usecase.schedule.find.findall.FindAllScheduleUseCase;
import com.aura8.general_backend.clean_arch.application.usecase.schedule.patch.PatchScheduleCommand;
import com.aura8.general_backend.clean_arch.application.usecase.schedule.patch.PatchScheduleUseCase;
import com.aura8.general_backend.clean_arch.core.domain.AvailableDay;
import com.aura8.general_backend.clean_arch.core.domain.Schedule;
import com.aura8.general_backend.clean_arch.infrastructure.dto.schedule.*;
import com.aura8.general_backend.clean_arch.infrastructure.mapper.ScheduleMapper;
import com.aura8.general_backend.clean_arch.infrastructure.enums.DirectionEnum;
import com.aura8.general_backend.dtos.jobscheduling.SchedulingPatchRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController("ScheduleControllerV2")
@RequestMapping("/v2/agendamentos")
public class ScheduleController {

    private final CreateScheduleUseCase createScheduleUseCase;
    private final DeleteScheduleUseCase deleteScheduleUseCase;
    private final FindByIdScheduleUseCase findByIdScheduleUseCase;
    private final FindAllScheduleUseCase findAllScheduleUseCase;
    private final GetAvailiblesDaysUseCase getAvailableTimesUseCase;
    private final PatchScheduleUseCase patchScheduleUseCase;

    public ScheduleController(CreateScheduleUseCase createScheduleUseCase, DeleteScheduleUseCase deleteScheduleUseCase, FindByIdScheduleUseCase findByIdScheduleUseCase, FindAllScheduleUseCase findAllScheduleUseCase, GetAvailiblesDaysUseCase getAvailableTimesUseCase, PatchScheduleUseCase patchScheduleUseCase) {
        this.createScheduleUseCase = createScheduleUseCase;
        this.deleteScheduleUseCase = deleteScheduleUseCase;
        this.findByIdScheduleUseCase = findByIdScheduleUseCase;
        this.findAllScheduleUseCase = findAllScheduleUseCase;
        this.getAvailableTimesUseCase = getAvailableTimesUseCase;
        this.patchScheduleUseCase = patchScheduleUseCase;
    }

    @CrossOrigin(origins = "*")
    @SecurityRequirement(name = "Bearer")
    @PostMapping
    @Operation(summary = "Criar agendamento", description = "Cria um novo agendamento com base nos dados fornecidos")
    @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro na validação dos dados da requisição")
    public ResponseEntity<ScheduleResponse> create(@Valid @RequestBody CreateScheduleRequest SchedulingRequestDto) {
        CreateScheduleCommand command = ScheduleMapper.toCreateScheduleCommand(SchedulingRequestDto);
        Schedule schedule = createScheduleUseCase.create(command);
        ScheduleResponse response = ScheduleMapper.toResponse(schedule);
        return ResponseEntity.status(201).body(response);
    }

    @CrossOrigin(origins = "*")
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar agendamento", description = "Deleta logicamente um agendamento pelo id")
    @ApiResponse(responseCode = "204", description = "Agendamento deletado logicamente com sucesso")
    @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do agendamento", example = "1")
            @PathVariable Integer id,
            @Parameter(description = "Role do usuário que está deletand o agendamento", example = "1")
            @RequestParam Integer roleId,
            @Parameter(description = "Motivo do cancelamento", example = "Fiquei doente")
            @RequestParam String message
    ) {
        deleteScheduleUseCase.cancel(id, roleId, message);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin(origins = "*")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/{id}")
    @Operation(summary = "Buscar agendamento por id", description = "Retorna os dados de um agendamento pelo id")
    @ApiResponse(responseCode = "200", description = "Agendamento encontrado")
    @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
    public ResponseEntity<ScheduleResponse> findById(@PathVariable Integer id) {
        Schedule schedule = findByIdScheduleUseCase.findById(id);
        ScheduleResponse response = ScheduleMapper.toResponse(schedule);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin(origins = "*")
    @SecurityRequirement(name = "Bearer")
    @GetMapping
    @Operation(summary = "Listar agendamentos paginados", description = "Retorna todos os agendamentos não deletados, paginados e ordenados")
    public ResponseEntity<Page<ScheduleResponse>> findAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") DirectionEnum direction
    ) {
        Page<Schedule> schedulePage = findAllScheduleUseCase.findAll(page, size, sortBy, direction.getDirection());
        Page<ScheduleResponse> responsePage = schedulePage.map(ScheduleMapper::toResponse);
        return ResponseEntity.ok(responsePage);
    }

    @CrossOrigin(origins = "*")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/card")
    public ResponseEntity<Page<ScheduleCardResponse>> getCardInfos(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") DirectionEnum direction
    ) {
        Page<Schedule> schedulePage = findAllScheduleUseCase.findAll(page, size, sortBy, direction.getDirection());
        Page<ScheduleCardResponse> cards = schedulePage.map(ScheduleMapper::toScheduleCardResponse);
        return ResponseEntity.ok(cards);
    }

    @CrossOrigin(origins = "*")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/available-times")
    public ResponseEntity<List<GetAvailableDaysResponse>> getAvaliablesTimes(
            @Parameter(description = "Duração em minutos", example = "30")
            @RequestParam Integer durationInMinutes,
            @Parameter(description = "Primeiro dia da semana (formato yyyy-MM-dd)", example = "2025-01-01")
            @RequestParam LocalDate firstDayOfWeek
    ) {
        List<AvailableDay> availablesDays = getAvailableTimesUseCase.getAvailablesDays(durationInMinutes, firstDayOfWeek);
        List<GetAvailableDaysResponse> availablesDaysDto = availablesDays.stream()
                .map(ScheduleMapper::toAvailableDayDto)
                .toList();
        return ResponseEntity.ok(availablesDaysDto);
    }

    @CrossOrigin(origins = "*")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/{idSchedule}")
    public ResponseEntity<ScheduleResponse> updateScheduling(
            @Parameter(description = "ID do agendamento", example = "1")
            @PathVariable Integer idSchedule,
            @Valid @RequestBody SchedulePatchRequest schedulingPatchRequest) {
        PatchScheduleCommand command = ScheduleMapper.toPatchScheduleCommand(schedulingPatchRequest);
        Schedule updatedSchedule = patchScheduleUseCase.patch(idSchedule, command);
        ScheduleResponse response = ScheduleMapper.toResponse(updatedSchedule);
        return ResponseEntity.ok(response);
    }
}
