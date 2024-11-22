package com.microservices.tournament.dto.team;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TournamentTeamCreateDto(
        @NotNull
        @Positive
        Long tournamentId,

        @NotNull
        @Positive
        Long teamId) {
}
