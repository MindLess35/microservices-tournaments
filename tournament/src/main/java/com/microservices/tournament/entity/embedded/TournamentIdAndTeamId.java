package com.microservices.tournament.entity.embedded;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;

@Embeddable
public record TournamentIdAndTeamId(
        @NotNull
        @Positive
        Long tournamentId,

        @NotNull
        @Positive
        Long teamId) implements Serializable {
}
