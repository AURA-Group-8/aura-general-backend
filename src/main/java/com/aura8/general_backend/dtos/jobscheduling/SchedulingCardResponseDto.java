package com.aura8.general_backend.dtos.jobscheduling;

import java.time.LocalDateTime;
import java.util.List;

public class SchedulingCardResponseDto {
    private Integer idScheduling;
    private String userName;
    private List<String> jobsNames;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    private Double totalPrice;
    private String paymentStatus;
    private String status;

    public Integer getIdScheduling() {
        return idScheduling;
    }

    public void setIdScheduling(Integer idScheduling) {
        this.idScheduling = idScheduling;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getJobsNames() {
        return jobsNames;
    }

    public void setJobsNames(List<String> jobsNames) {
        this.jobsNames = jobsNames;
    }

    public LocalDateTime getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(LocalDateTime startDatetime) {
        this.startDatetime = startDatetime;
    }

    public LocalDateTime getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(LocalDateTime endDatetime) {
        this.endDatetime = endDatetime;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
