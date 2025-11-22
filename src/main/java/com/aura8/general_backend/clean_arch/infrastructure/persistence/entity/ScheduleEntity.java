package com.aura8.general_backend.clean_arch.infrastructure.persistence.entity;

import com.aura8.general_backend.clean_arch.core.domain.enums.PaymentStatus;
import com.aura8.general_backend.clean_arch.core.domain.enums.ScheduleStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "schedule")
public class ScheduleEntity extends AuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private UsersEntity users;
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<JobScheduleEntity> jobSchedules;
    private Integer feedback;
    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private Double totalPrice;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    private Boolean canceled = false;

    public Integer getId() {
        return id;
    }

    public List<JobScheduleEntity> getJobSchedules() {
        return jobSchedules;
    }

    public void setJobSchedules(List<JobScheduleEntity> jobSchedules) {
        this.jobSchedules = jobSchedules;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UsersEntity getUsers() {
        return users;
    }

    public void setUsers(UsersEntity users) {
        this.users = users;
    }

    public Integer getFeedback() {
        return feedback;
    }

    public void setFeedback(Integer feedback) {
        this.feedback = feedback;
    }

    public ScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduleStatus status) {
        this.status = status;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
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
        this.startDatetime = startDatetime;
    }

    public LocalDateTime getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(LocalDateTime endDatetime) {
        this.endDatetime = endDatetime;
    }

    public Boolean getCanceled() {
        return canceled;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }
}
