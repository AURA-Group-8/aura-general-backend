package com.aura8.general_backend.core.domain.attribute;

public class Email {
    private String value;

    public Email(String value) {
        if (value == null || !value.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9]+.[A-Za-z0-9]+$")) {
            throw new IllegalArgumentException("Email %s não é válido".formatted(value));
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
