package com.microservices.team.dto.statistics;

public record TeamStatisticsReadDto(
        Long teamId,
        Integer matchesPlayed,
        Integer wins,
        Integer losses,
        Integer draws) {
}

