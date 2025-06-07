package com.aura8.general_backend.controller;

import com.aura8.general_backend.dtos.jobscheduling.AvailableDayDto;
import com.aura8.general_backend.dtos.jobscheduling.JobSchedulingRequestDto;
import com.aura8.general_backend.dtos.jobscheduling.SchedulingCardResponseDto;
import com.aura8.general_backend.dtos.jobscheduling.SchedulingPatchRequestDto;
import com.aura8.general_backend.entities.Scheduling;
import com.aura8.general_backend.service.JobSchedulingService;
import com.aura8.general_backend.service.SchedulingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@Tag(name = "Agendamentos", description = "Controlador de agendamentos")
public class JobSchedulingController {

    private JobSchedulingService jobSchedulingService;
    private SchedulingService schedulingService;

    public JobSchedulingController(JobSchedulingService jobSchedulingService, SchedulingService schedulingService) {
        this.jobSchedulingService = jobSchedulingService;
        this.schedulingService = schedulingService;
    }

    @CrossOrigin(origins = "*")
    @SecurityRequirement(name = "Bearer")
    @PostMapping
    @Operation(summary = "Criar agendamento", description = "Cria um novo agendamento com base nos dados fornecidos")
    @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro na validação dos dados da requisição")
    public ResponseEntity<Scheduling> create(@Valid @RequestBody JobSchedulingRequestDto jobSchedulingRequestDto){
        return ResponseEntity.status(201).body(
                jobSchedulingService.create(
                        jobSchedulingRequestDto.getUserId(),
                        jobSchedulingRequestDto.getJobsIds(),
                        jobSchedulingRequestDto.getStartDatetime()
                ));
    }

    @CrossOrigin(origins = "*")
    @SecurityRequirement(name = "Bearer")
    @GetMapping
    @Operation(summary = "Listar agendamentos", description = "Retorna uma lista paginada de todos os agendamentos")
    @ApiResponse(responseCode = "200", description = "Lista de agendamentos retornada com sucesso")
    public ResponseEntity<Page<Scheduling>> getAll(@ParameterObject Pageable pageable){
        return ResponseEntity.ok(schedulingService.findAll(pageable));
    }

    @CrossOrigin(origins = "*")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/available-times")
    public ResponseEntity<List<AvailableDayDto>> getAvaliablesTimes(
            @Parameter(description = "Duração em minutos", example = "30")
            @RequestParam Integer durationInMinutes,
            @Parameter(description = "Primeiro dia da semana (formato yyyy-MM-dd)", example = "2025-01-01")
            @RequestParam LocalDate firstDayOfWeek
            ) {
        return ResponseEntity.ok(schedulingService.getAvailableTimes(durationInMinutes, firstDayOfWeek));
    }

    @CrossOrigin(origins = "*")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/card")
    public ResponseEntity<List<SchedulingCardResponseDto>> getCardInfos() {
        List<SchedulingCardResponseDto> cards = schedulingService.getCardInfos();
        cards.forEach(card -> {
            card.setJobsNames(
                    jobSchedulingService.getJobsInScheduling(card.getIdScheduling())
                            .stream()
                            .map(job -> job.getName())
                            .toList()
            );
            });
        if( cards.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cards);
    }

    @CrossOrigin(origins = "*")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/{idScheduling}")
    public ResponseEntity<Scheduling> updateScheduling(
            @Parameter(description = "ID do agendamento", example = "1")
            @PathVariable Integer idScheduling,
            @Valid @RequestBody SchedulingPatchRequestDto schedulingPatchRequestDto) {
        Scheduling updatedScheduling = schedulingService.update(idScheduling, schedulingPatchRequestDto);
        return ResponseEntity.ok(updatedScheduling);
    }

    @CrossOrigin(origins = "*")
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/{idScheduling}")
    public ResponseEntity<Void> deleteScheduling(
            @Parameter(description = "ID do agendamento", example = "1")
            @PathVariable Integer idScheduling) {
        schedulingService.delete(idScheduling);
        return ResponseEntity.noContent().build();
    }
}
