package com.aura8.general_backend.controller;

import com.aura8.general_backend.dtos.job.JobMapper;
import com.aura8.general_backend.dtos.job.JobResponseDto;
import com.aura8.general_backend.entities.Job;
import com.aura8.general_backend.service.JobService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/servicos")
public class JobController {

    private final JobService service;

    public JobController(JobService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<JobResponseDto>> getAll(Pageable pageable){
        Page<Job> jobsPage = service.getJobs(pageable);
        Page<JobResponseDto> jobsDtoPage = JobMapper.toResponse(jobsPage);
        return ResponseEntity.ok(jobsDtoPage);
    }
}
