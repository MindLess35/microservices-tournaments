package com.microservices.match.dto;

import com.microservices.match.enums.MatchResult;
import com.microservices.match.enums.MatchStatus;
import com.microservices.match.enums.SportType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record MatchUpdateDto(
        SportType sportType,
        @FutureOrPresent
        Instant startDate,
        @Future
        Instant endDate,
        @Size(max = 255)
        String location,
        MatchResult result,
        @PositiveOrZero
        Integer scoreFirstTeam,
        @PositiveOrZero
        Integer scoreSecondTeam,
        MatchStatus status) {
}