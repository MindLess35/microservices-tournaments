package com.microservices.match.dto;

import com.microservices.match.enums.SportType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record MatchCreateDto(
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

        @NotNull
        @Positive
        Long tournamentId,

        @NotNull
        @Positive
        Long firstTeamId,

        @NotNull
        @Positive
        Long secondTeamId) {
}