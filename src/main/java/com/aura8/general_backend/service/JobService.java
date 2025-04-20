package com.aura8.general_backend.service;

import com.aura8.general_backend.entities.Job;
import com.aura8.general_backend.exception.ElementNotFoundException;
import com.aura8.general_backend.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {
    @Autowired
    private JobRepository repository;

    public Job createService(Job job){
        return repository.save(job);
    }

    public Page<Job> getJobs(Pageable pageable){
        return repository.findAll(pageable);
    }

    public List<Job> getJobsInList(List<Integer> jobsIds){
        List<Integer> notFoundedIds = new ArrayList<>();
        List<Job> foundedJobs = jobsIds.stream()
                .map((jobId) -> {
                    Job job = getByIdForList(jobId);
                    if(job == null) notFoundedIds.add(jobId);
                    return job;
                })
                .toList();
        if(!notFoundedIds.isEmpty()) throw new ElementNotFoundException("Jobs de IDs: %s não foram encontrados".formatted(notFoundedIds.toString()));
        return foundedJobs;
    }

    public Job getById(Integer id){
        return repository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("Job de ID: %d não foi encontrado".formatted(id))
        );
    }

    public Job getByIdForList(Integer id){
        return repository.findById(id).orElse(null);
    }

    public Double getTotalPrice(List<Integer> jobsIds){
        return repository.sumPrices(jobsIds);
    }

    public Long getTotalTime(List<Integer> jobsIds){
        return repository.sumTimes(jobsIds);
    }
}
