package com.microservices.tournament.dto.team;

import java.time.Instant;

public record TournamentTeamReadDto(
        Long tournamentId,
        Long teamId,
        Instant registrationDate,
        Boolean isApproved) {
}
