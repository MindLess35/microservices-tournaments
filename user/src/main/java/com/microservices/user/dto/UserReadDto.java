package com.microservices.user.dto;

import com.microservices.user.enums.Gender;
import com.microservices.user.enums.Role;

import java.time.Instant;

public record UserReadDto(
        Long id,
        String username,
        String email,
        String about,
        Gender gender,
        Role role,
        Instant createdAt,
        Instant updatedAt) {
}