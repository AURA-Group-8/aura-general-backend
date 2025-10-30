package com.aura8.general_backend.clean_arch.core.domain.enums;

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
