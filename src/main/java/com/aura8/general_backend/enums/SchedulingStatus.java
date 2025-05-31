package com.aura8.general_backend.enums;

public enum SchedulingStatus {
    FEITO("Feito"),
    PENDENTE("Pendente"),
    CANCELADO("Cancelado");


    private final String status;
    SchedulingStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
