package com.aura8.general_backend.dtos.jobscheduling;

import io.swagger.v3.oas.annotations.media.Schema;

public class SchedulingPatchRequestDto {
    @Schema(description = "Id do usuario", example = "1")
    private Integer id;
    @Schema(description = "Feedback do agendamento", example = "1")
    private Integer feedback;
    @Schema(description = "Status do Agendamento", example = "PENDENTE")
    private String status;
    @Schema(description = "Status do Pagamento", example = "PENDENTE")
    private String paymentStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFeedback() {
        return feedback;
    }

    public void setFeedback(Integer feedback) {
        this.feedback = feedback;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
