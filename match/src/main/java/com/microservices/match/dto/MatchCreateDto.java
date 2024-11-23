package com.microservices.match.dto;

import com.microservices.match.enums.SportType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
public final class MatchCreateDto {

    @NotNull
    private final SportType sportType;

    @NotNull
    @FutureOrPresent
    private final Instant startDate;

    @NotNull
    @Future
    private final Instant endDate;

    @Size(max = 255)
    private final String location;

    @NotNull
    @Positive
    private final Long tournamentId;

    @NotNull
    @Positive
    private Long firstTeamId;

    @NotNull
    @Positive
    private Long secondTeamId;

}