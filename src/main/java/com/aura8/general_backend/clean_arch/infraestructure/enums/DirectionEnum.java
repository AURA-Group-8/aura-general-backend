package com.aura8.general_backend.clean_arch.infraestructure.enums;

public enum DirectionEnum {
    ASC("ASC"),
    DESC("DESC");

    private final String direction;

    DirectionEnum(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }
}
