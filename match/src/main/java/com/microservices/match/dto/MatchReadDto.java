package com.microservices.match.dto;

import com.microservices.match.enums.MatchResult;
import com.microservices.match.enums.MatchStatus;
import com.microservices.match.enums.SportType;

import java.time.Instant;

public record MatchReadDto(
        Long id,
        SportType sportType,
        Instant startDate,
        Instant endDate,
        String location,
        MatchResult result,
        Integer scoreFirstTeam,
        Integer scoreSecondTeam,
        MatchStatus status,
        Long tournamentId,
        Long firstTeamId,
        Long secondTeamId) {}