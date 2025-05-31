package com.aura8.general_backend.controller;

import com.aura8.general_backend.dtos.job.*;
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
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("")
    @Operation(summary = "Listar serviços", description = "Retorna uma lista paginada de todos os serviços disponíveis")
    @ApiResponse(responseCode = "200", description = "Lista de serviços retornada com sucesso")
    public ResponseEntity<Page<JobResponseDto>> getAll(@ParameterObject Pageable pageable){
        Page<Job> jobsPage = service.getJobs(pageable);
        Page<JobResponseDto> jobsDtoPage = JobMapper.toResponse(jobsPage);
        return ResponseEntity.ok(jobsDtoPage);
    }


    @CrossOrigin(origins = "*")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/{id}")
    @Operation(summary = "Buscar serviço por ID", description = "Retorna um serviço específico pelo ID fornecido")
    @ApiResponse(responseCode = "200", description = "Serviço encontrado com sucesso")
    public ResponseEntity<JobResponseDto> getById(@PathVariable Integer id) {
        Job job = service.getById(id);
        return ResponseEntity.ok(JobMapper.toResponse(job));
    }

    @CrossOrigin(origins = "*")
    @SecurityRequirement(name = "Bearer")
    @PostMapping
    @Operation(summary = "Criar serviço", description = "Cria um novo serviço com os dados fornecidos")
    @ApiResponse(responseCode = "201", description = "Serviço criado com sucesso")
    public ResponseEntity<JobResponseDto> create(@RequestBody JobRequestDto jobRequestDto) {
        Job job = JobMapper.toEntity(jobRequestDto);
        Job createdJob = service.createService(job);
        return ResponseEntity.status(201).body(JobMapper.toResponse(createdJob));
    }
    @CrossOrigin(origins = "*")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar serviço", description = "Atualiza um serviço existente com os dados fornecidos")
    @ApiResponse(responseCode = "200", description = "Serviço atualizado com sucesso")
    public ResponseEntity<JobResponseDto> updateById(@PathVariable Integer id, @RequestBody JobRequestDto jobUpdateDto) {
        Job job = JobMapper.toEntity(jobUpdateDto);
        Job updatedJob = service.updateById(id, job);
        return ResponseEntity.ok(JobMapper.toResponse(updatedJob));

    }
    @CrossOrigin(origins = "*")
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar serviço", description = "Deleta um serviço existente pelo ID fornecido")
    @ApiResponse(responseCode = "200", description = "Serviço deletado com sucesso")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        Job job = service.getById(id);
        service.deleteById(job.getId());
        return ResponseEntity.noContent().build();
    }
}
