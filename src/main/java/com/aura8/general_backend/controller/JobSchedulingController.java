package com.aura8.general_backend.controller;

import com.aura8.general_backend.dtos.jobscheduling.JobSchedulingRequestDto;
import com.aura8.general_backend.entities.Scheduling;
import com.aura8.general_backend.service.JobSchedulingService;
import com.aura8.general_backend.service.SchedulingService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agendamentos")
public class JobSchedulingController {

    private JobSchedulingService jobSchedulingService;
    private SchedulingService schedulingService;

    public JobSchedulingController(JobSchedulingService jobSchedulingService, SchedulingService schedulingService) {
        this.jobSchedulingService = jobSchedulingService;
        this.schedulingService = schedulingService;
    }

    @PostMapping
    public ResponseEntity<Scheduling> create(@Valid @RequestBody JobSchedulingRequestDto jobSchedulingRequestDto){
        return ResponseEntity.status(201).body(
                jobSchedulingService.create(
                        jobSchedulingRequestDto.getUserId(),
                        jobSchedulingRequestDto.getJobsIds(),
                        jobSchedulingRequestDto.getStartDatetime()
                ));
    }

    @GetMapping
    public ResponseEntity<Page<Scheduling>> getAll(Pageable pageable){
        return ResponseEntity.ok(schedulingService.findAll(pageable));
    }
}
