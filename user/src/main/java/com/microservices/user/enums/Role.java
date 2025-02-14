package com.microservices.user.enums;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_USER("USER"),
    ROLE_PLAYER("PLAYER"),
    ROLE_TRAINER("TRAINER"),
    ROLE_ORGANIZER("ORGANIZER"),
    ROLE_ADMIN("ADMIN");

    private final String value;

    Role(String value) {
        this.value = value;
    }

}