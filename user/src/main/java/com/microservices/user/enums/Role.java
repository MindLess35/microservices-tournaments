package com.microservices.user.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    PLAYER("player"),
    TRAINER("trainer"),
    ORGANIZER("organizer"),
    ADMIN("admin");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Role fromValue(String value) {
        for (Role role : Role.values()) {
            if (role.value.equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + value);
    }
}