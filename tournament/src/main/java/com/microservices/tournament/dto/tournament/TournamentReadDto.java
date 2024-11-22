package com.microservices.tournament.dto.tournament;

import com.microservices.tournament.enums.SportType;
import com.microservices.tournament.enums.TournamentStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record TournamentReadDto(
        Long id,
        String name,
        SportType sportType,
        Instant startDate,
        Instant endDate,
        String location,
        BigDecimal prizePool,
        TournamentStatus status,
        Instant createdAt,
        Instant updatedAt) {
}
