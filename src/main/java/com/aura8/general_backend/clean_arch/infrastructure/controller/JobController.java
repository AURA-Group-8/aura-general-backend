package com.aura8.general_backend.clean_arch.infrastructure.controller;

import com.aura8.general_backend.clean_arch.application.usecase.job.create.CreateJobCommand;
import com.aura8.general_backend.clean_arch.application.usecase.job.create.CreateJobUseCase;
import com.aura8.general_backend.clean_arch.application.usecase.job.delete.DeleteJobUseCase;
import com.aura8.general_backend.clean_arch.application.usecase.job.find.findall.FindAllJobUseCase;
import com.aura8.general_backend.clean_arch.application.usecase.job.find.findbyid.FindByIdJobUseCase;
import com.aura8.general_backend.clean_arch.application.usecase.job.patch.PatchJobCommand;
import com.aura8.general_backend.clean_arch.application.usecase.job.patch.PatchJobUseCase;
import com.aura8.general_backend.clean_arch.core.domain.Job;
import com.aura8.general_backend.clean_arch.infrastructure.dto.job.*;
import com.aura8.general_backend.clean_arch.infrastructure.mapper.JobMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("JobControllerV2")
@RequestMapping("v2/servicos")
public class JobController {
    private final CreateJobUseCase createJobUseCase;
    private final FindAllJobUseCase findAllJobUseCase;
    private final FindByIdJobUseCase findJobByIdUseCase;
    private final PatchJobUseCase patchJobUseCase;
    private final DeleteJobUseCase deleteJobUseCase;

    public JobController(CreateJobUseCase createJobUseCase, FindAllJobUseCase findAllJobUseCase, FindByIdJobUseCase findJobByIdUseCase, PatchJobUseCase patchJobUseCase, DeleteJobUseCase deleteJobUseCase) {
        this.createJobUseCase = createJobUseCase;
        this.findAllJobUseCase = findAllJobUseCase;
        this.findJobByIdUseCase = findJobByIdUseCase;
        this.patchJobUseCase = patchJobUseCase;
        this.deleteJobUseCase = deleteJobUseCase;
    }

    @CrossOrigin(origins = "*")
    @SecurityRequirement(name = "Bearer")
    @PostMapping
    @Operation(summary = "Criar serviço", description = "Cria um novo serviço com os dados fornecidos")
    @ApiResponse(responseCode = "201", description = "Serviço criado com sucesso")
    public ResponseEntity<CreateJobResponse> create(@Valid @RequestBody CreateJobRequest jobRequestDto) {
        CreateJobCommand jobCommand = JobMapper.toCreateCommand(jobRequestDto);
        Job createdJob = createJobUseCase.execute(jobCommand);
        CreateJobResponse responseDto = JobMapper.toCreateResponse(createdJob);
        return ResponseEntity.status(201).body(responseDto);
    }

    @CrossOrigin(origins = "*")
    @SecurityRequirement(name = "Bearer")
    @GetMapping
    @Operation(summary = "Listar serviços", description = "Retorna uma lista paginada de todos os serviços disponíveis")
    @ApiResponse(responseCode = "200", description = "Lista de serviços retornada com sucesso")
    @ApiResponse(responseCode = "204", description = "Lista de serviços retornada com sucesso e vazia")
    public ResponseEntity<List<FindJobResponse>> getAll(){
        List<Job> jobs = findAllJobUseCase.findAll();
        if(jobs == null || jobs.isEmpty()) return ResponseEntity.noContent().build();
        List<FindJobResponse> jobsDto = jobs.stream().map(JobMapper::toFindResponse).toList();
        return ResponseEntity.ok(jobsDto);
    }

    @CrossOrigin(origins = "*")
    @SecurityRequirement(name = "Bearer")
    @GetMapping("/{id}")
    @Operation(summary = "Buscar serviço por ID", description = "Retorna um serviço específico pelo ID fornecido")
    @ApiResponse(responseCode = "200", description = "Serviço encontrado com sucesso")
    public ResponseEntity<FindJobResponse> getById(@PathVariable Integer id) {
        Optional<Job> optionalJob = findJobByIdUseCase.findById(id);
        if(optionalJob.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Job job = optionalJob.get();
        return ResponseEntity.ok(JobMapper.toFindResponse(job));
    }

    @CrossOrigin(origins = "*")
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar serviço", description = "Atualiza um serviço existente com os dados fornecidos")
    @ApiResponse(responseCode = "200", description = "Serviço atualizado com sucesso")
    @ApiResponse(responseCode = "204", description = "Serviço não encontrado")
    public ResponseEntity<PatchJobResponse> updateById(@PathVariable Integer id, @Valid @RequestBody PatchJobRequest patchJobRequest) {
        PatchJobCommand patchJobCommand = JobMapper.toPatchCommand(patchJobRequest);
        Job updateJob = patchJobUseCase.patch(patchJobCommand, id);
        return ResponseEntity.ok(JobMapper.toPatchResponse(updateJob));
    }

    @CrossOrigin(origins = "*")
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar serviço", description = "Deleta um serviço existente pelo ID fornecido")
    @ApiResponse(responseCode = "204", description = "Serviço deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Serviço Não encontrado")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        deleteJobUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
