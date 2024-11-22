package com.microservices.tournament.dto.tournament;

import com.microservices.tournament.enums.SportType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;

public record TournamentCreateDto(
        @NotBlank
        @Size(max = 255)
        String name,

        @NotNull
        SportType sportType,

        @NotNull
        @FutureOrPresent
        Instant startDate,

        @NotNull
        @Future
        Instant endDate,

        @Size(max = 255)
        String location,

        @PositiveOrZero
        BigDecimal prizePool) {
}
