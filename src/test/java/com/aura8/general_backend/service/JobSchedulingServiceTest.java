package com.aura8.general_backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import com.aura8.general_backend.entities.*;
import com.aura8.general_backend.exception.ElementNotFoundException;

import com.aura8.general_backend.repository.JobSchedulingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JobSchedulingServiceTest {

    @Mock
    private JobService jobService;

    @Mock
    private SchedulingService schedulingService;

    @Mock
    private JobSchedulingRepository jobSchedulingRepository;

    @InjectMocks
    private JobSchedulingService service;

    @Test
    @DisplayName("Testar a criação um agendamento de acordo com lista de serviços válida")
    void shouldCreateSchedulingSuccessfully() {
        Integer userId = 1;
        List<Integer> jobIds = List.of(10, 20);
        LocalDateTime start = LocalDateTime.of(2025, 5, 30, 10, 0);

        Job job1 = new Job(); job1.setId(10); job1.setPrice(100.0);
        Job job2 = new Job(); job2.setId(20); job2.setPrice(200.0);
        List<Job> jobs = List.of(job1, job2);

        Scheduling savedScheduling = new Scheduling();
        savedScheduling.setId(99);
        savedScheduling.setStartDatetime(start);
        savedScheduling.setEndDatetime(start.plusMinutes(60));
        savedScheduling.setTotalPrice(300.0);

        when(jobService.getJobsInList(jobIds)).thenReturn(jobs);
        when(jobService.getTotalPrice(jobIds)).thenReturn(300.0);
        when(jobService.getTotalTime(jobIds)).thenReturn(60L);
        when(schedulingService.create(any(Scheduling.class), eq(userId), anyInt())).thenReturn(savedScheduling);

        Scheduling result = service.create(userId, jobIds, start,1);

        assertEquals(99, result.getId());
        assertEquals(300.0, result.getTotalPrice());
        assertEquals(start.plusMinutes(60), result.getEndDatetime());

        verify(jobSchedulingRepository, times(2)).save(any(JobScheduling.class));
    }

    @Test
    @DisplayName("Testar o lançamento da exceção de elemento não encontrado ao tentar criar agendamento com lista de serviços vazia")
    void shouldThrowExceptionWhenJobListIsEmpty() {
        Integer userId = 1;
        List<Integer> jobIds = List.of();
        LocalDateTime start = LocalDateTime.now();

        assertThrows(ElementNotFoundException.class, () -> {
            service.create(userId, jobIds, start,1);
        });

        verify(jobSchedulingRepository, never()).save(any());
    }

    @Test
    @DisplayName("Testar a associação de todos os serviços ao agendamento corretamente")
    void shouldAssociateAllJobsWithScheduling() {
        Integer userId = 1;
        List<Integer> jobIds = List.of(1, 2);
        LocalDateTime start = LocalDateTime.now();

        Job job1 = new Job(); job1.setId(1); job1.setPrice(100.0);
        Job job2 = new Job(); job2.setId(2); job2.setPrice(150.0);
        List<Job> jobs = List.of(job1, job2);

        Scheduling scheduling = new Scheduling(); scheduling.setId(55);

        when(jobService.getJobsInList(jobIds)).thenReturn(jobs);
        when(jobService.getTotalPrice(jobIds)).thenReturn(250.0);
        when(jobService.getTotalTime(jobIds)).thenReturn(50L);
        when(schedulingService.create(any(Scheduling.class), eq(userId), anyInt())).thenReturn(scheduling);

        service.create(userId, jobIds, start, null);

        ArgumentCaptor<JobScheduling> captor = ArgumentCaptor.forClass(JobScheduling.class);
        verify(jobSchedulingRepository, times(2)).save(captor.capture());

        List<JobScheduling> saved = captor.getAllValues();
        assertEquals(2, saved.size());
        assertEquals(55, saved.get(0).getScheduling().getId());
    }

}