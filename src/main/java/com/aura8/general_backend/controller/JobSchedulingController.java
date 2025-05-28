package com.aura8.general_backend.controller;

import com.aura8.general_backend.dtos.jobscheduling.AvailableDayDto;
import com.aura8.general_backend.dtos.jobscheduling.JobSchedulingRequestDto;
import com.aura8.general_backend.entities.Scheduling;
import com.aura8.general_backend.service.JobSchedulingService;
import com.aura8.general_backend.service.SchedulingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

    @GetMapping
    @Operation(summary = "Listar agendamentos", description = "Retorna uma lista paginada de todos os agendamentos")
    @ApiResponse(responseCode = "200", description = "Lista de agendamentos retornada com sucesso")
    public ResponseEntity<Page<Scheduling>> getAll(Pageable pageable){
        return ResponseEntity.ok(schedulingService.findAll(pageable));
    }

    @GetMapping("/available-times")
    public ResponseEntity<List<AvailableDayDto>> getAvaliablesTimes(
            @RequestParam Integer durationInMinutes,
            @RequestParam LocalDate firstDayOfWeek
            ) {
        return ResponseEntity.ok(schedulingService.getAvailableTimes(durationInMinutes, firstDayOfWeek));
    }
}
