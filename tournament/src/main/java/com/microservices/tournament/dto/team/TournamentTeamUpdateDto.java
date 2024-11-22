package com.microservices.tournament.dto.team;

import jakarta.validation.constraints.NotNull;

public record TournamentTeamUpdateDto(
        @NotNull
        Boolean isApproved) {
}
