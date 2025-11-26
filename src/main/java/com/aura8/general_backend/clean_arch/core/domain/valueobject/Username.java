package com.aura8.general_backend.clean_arch.core.domain.valueobject;

public class Username {
    private String value;

    public Username(String value) {
        if (value == null || value.length() < 3) {
            throw new IllegalArgumentException("Username deve ter no mínimo 3 caracteres");
        }
        this.value = value;
    }

    public String get() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
