package com.aura8.general_backend.clean_arch.core.domain;

import com.aura8.general_backend.enums.PaymentStatus;
import com.aura8.general_backend.enums.SchedulingStatus;

import java.time.LocalDateTime;
import java.util.List;

public class Schedule {
    private Integer id;
    private Users users;
    private List<Job> jobs;
    private Double totalPrice;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    private SchedulingStatus status = SchedulingStatus.PENDENTE;
    private PaymentStatus paymentStatus = PaymentStatus.PENDENTE;
    private Integer feedback;
    private LocalDateTime canceledAt;
    private Boolean canceled = false;

    public Schedule(Users users, List<Job> jobs, LocalDateTime startDatetime, LocalDateTime endDatetime) {
        this.users = users;
        this.jobs = jobs;
        this.startDatetime = startDatetime;
        this.endDatetime = validateEndDatetime(endDatetime);
        this.totalPrice = calculeteTotalPrice(jobs);
    }

    public Schedule(Integer id, Users users, List<Job> jobs, Double totalPrice, LocalDateTime startDatetime, LocalDateTime endDatetime) {
        this.users = users;
        this.jobs = jobs;
        this.startDatetime = startDatetime;
        this.endDatetime = validateEndDatetime(endDatetime);
        this.totalPrice = totalPrice;
    }

    public LocalDateTime validateStartDatetime(LocalDateTime startDatetime) {
        if(endDatetime == null) {
            return startDatetime;
        }
        if (startDatetime.isAfter(endDatetime)) {
            throw new IllegalArgumentException("Data de início deve ser antes da data de término.");
        }
        return startDatetime;
    }

    public LocalDateTime validateEndDatetime(LocalDateTime endDatetime) {
        if(startDatetime == null) {
            return endDatetime;
        }
        if (endDatetime.isBefore(startDatetime)) {
            throw new IllegalArgumentException("Data de término deve ser depois da data de início.");
        }
        return endDatetime;
    }

    public Double calculeteTotalPrice(List<Job> jobs) {
        Double price = jobs.stream().mapToDouble(Job::getPrice).sum();
        if (price <= 0) {
            throw new IllegalArgumentException("O preço total deve ser maior que zero.");
        }
        return price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users getUsers() {
        return users;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(LocalDateTime startDatetime) {
        this.startDatetime = validateStartDatetime(startDatetime);
    }

    public LocalDateTime getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(LocalDateTime endDatetime) {
        this.endDatetime = validateEndDatetime(endDatetime);
    }

    public SchedulingStatus getStatus() {
        return status;
    }

    public void setStatus(SchedulingStatus status) {
        this.status = status;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Integer getFeedback() {
        return feedback;
    }

    public void setFeedback(Integer feedback) {
        this.feedback = feedback;
    }

    public LocalDateTime getCanceledAt() {
        return canceledAt;
    }

    public Boolean isCanceled() {
        return canceled;
    }

    public void cancel() {
        canceled = true;
        canceledAt = LocalDateTime.now();
    }
}