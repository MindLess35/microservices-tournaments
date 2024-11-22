package com.microservices.tournament.dto.tournament;

import com.microservices.tournament.enums.SportType;
import com.microservices.tournament.enums.TournamentStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;

public record TournamentUpdateDto(
        @Size(max = 255)
        String name,

        SportType sportType,

        @FutureOrPresent
        Instant startDate,

        @Future
        Instant endDate,

        @Size(max = 255)
        String location,

        @PositiveOrZero
        BigDecimal prizePool,

        TournamentStatus status) {
}

