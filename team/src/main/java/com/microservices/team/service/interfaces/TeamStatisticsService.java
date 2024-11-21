package com.microservices.team.service.interfaces;

import com.microservices.team.dto.statistics.TeamStatisticsReadDto;
import com.microservices.team.enums.MatchResultType;

public interface TeamStatisticsService {
    TeamStatisticsReadDto findById(Long teamId);

    TeamStatisticsReadDto updateStatistics(Long teamId, MatchResultType matchResultType);

}
