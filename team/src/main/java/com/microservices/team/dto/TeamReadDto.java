package com.microservices.team.dto;

public record TeamReadDto(
        Long id,

        String name,

        String about,

        Long captainId) {
}