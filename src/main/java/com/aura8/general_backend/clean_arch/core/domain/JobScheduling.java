package com.aura8.general_backend.clean_arch.core.domain;

public class JobScheduling {
    private Integer id;
    private Schedule schedule;
    private Job job;
    private Double currentPrice;

    public JobScheduling(Integer id, Schedule schedule, Job job) {
        this.id = id;
        this.schedule = schedule;
        this.job = job;
        this.currentPrice = job.getPrice();
    }

    public JobScheduling(Integer id, Schedule schedule, Job job, Double currentPrice) {
        this.id = id;
        this.schedule = schedule;
        this.job = job;
        this.currentPrice = currentPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Schedule getScheduling() {
        return schedule;
    }

    public void setScheduling(Schedule schedule) {
        this.schedule = schedule;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }
}
