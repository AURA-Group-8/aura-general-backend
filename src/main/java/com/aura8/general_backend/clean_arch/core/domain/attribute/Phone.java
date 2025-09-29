package com.aura8.general_backend.clean_arch.core.domain.attribute;

public class Phone {
    private String value;

    public Phone(String value) {
        if (value == null || !value.matches("^[0-9]{11}$")) {
            throw new IllegalArgumentException("Phone %s não é válido".formatted(value));
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
