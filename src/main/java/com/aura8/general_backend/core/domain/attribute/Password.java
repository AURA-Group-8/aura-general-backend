package com.aura8.general_backend.core.domain.attribute;

public class Password {
    private String value;

    public Password(String value) {
        if (value == null || value.length() < 8) {
            throw new IllegalArgumentException("Password deve ter no mínimo 8 caracteres");
        }
        this.value = value;
    }

    public String get() {
        return value;
    }
}
