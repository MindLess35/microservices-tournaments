package com.microservices.team.dto.team;

import com.microservices.team.enums.SportType;

import java.time.Instant;

public record TeamReadDto(
        Long id,
        String name,
        String about,
        SportType sportType,
        Long captainId,
        Instant createdAt,
        Instant updatedAt) {
}