package com.microservices.team.dto.team;

public record TeamReadDto(
        Long id,

        String name,

        String about,

        Long captainId) {
}