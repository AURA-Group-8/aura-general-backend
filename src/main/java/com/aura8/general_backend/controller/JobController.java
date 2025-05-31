package com.aura8.general_backend.controller;

import com.aura8.general_backend.dtos.job.JobMapper;
import com.aura8.general_backend.dtos.job.JobResponseDto;
import com.aura8.general_backend.entities.Job;
import com.aura8.general_backend.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/servicos")
@Tag(name = "Serviços", description = "Controlador de serviços da cliente")
public class JobController {

    private final JobService service;

    public JobController(JobService service) {
        this.service = service;
    }

    @CrossOrigin(origins = "*")
    @SecurityRequirement(name = "Bearer")
    @GetMapping
    @Operation(summary = "Listar serviços", description = "Retorna uma lista paginada de todos os serviços disponíveis")
    @ApiResponse(responseCode = "200", description = "Lista de serviços retornada com sucesso")
    public ResponseEntity<Page<JobResponseDto>> getAll(@ParameterObject Pageable pageable){
        Page<Job> jobsPage = service.getJobs(pageable);
        Page<JobResponseDto> jobsDtoPage = JobMapper.toResponse(jobsPage);
        return ResponseEntity.ok(jobsDtoPage);
    }
}
