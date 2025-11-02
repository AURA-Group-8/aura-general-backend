package com.aura8.general_backend.clean_arch.core.domain.enums;

public enum PaymentStatus {
    PAGO("Pago"),
    PENDENTE("Pendente");

    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
