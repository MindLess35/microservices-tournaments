package com.microservices.user.dto.user;

import com.microservices.user.enums.Gender;
import com.microservices.user.enums.Role;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;
import java.util.UUID;

public record UserReadDto(
        Long id,
        UUID keycloakUuid,
        String username,
        String firstName,
        String lastName,
        String email,
        String about,
        Gender gender,
        Role role,
        Instant createdAt,
        Instant updatedAt) {
}