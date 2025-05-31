package com.aura8.general_backend.enums;

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
