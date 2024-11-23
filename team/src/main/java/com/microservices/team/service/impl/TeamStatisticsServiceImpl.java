package com.microservices.team.service.impl;

import com.common.exception.exception.base.NotFoundBaseException;
import com.microservices.team.dto.statistics.TeamStatisticsReadDto;
import com.microservices.team.entity.TeamStatistics;
import com.microservices.team.enums.MatchResultType;
import com.microservices.team.event.TeamCreationEvent;
import com.microservices.team.mapper.TeamStatisticsMapper;
import com.microservices.team.repository.TeamStatisticsRepository;
import com.microservices.team.service.interfaces.TeamStatisticsService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamStatisticsServiceImpl implements TeamStatisticsService {
    private final TeamStatisticsRepository teamStatisticsRepository;
    private final TeamStatisticsMapper teamStatisticsMapper;
    @PersistenceContext
    private final EntityManager entityManager;
    private static final String TEAM_STATS_NOT_FOUND = "Team with id [%d] not found";

    @Override
    public TeamStatisticsReadDto findById(Long teamId) {
        return teamStatisticsMapper.toDto(teamStatisticsRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundBaseException(TEAM_STATS_NOT_FOUND.formatted(teamId))));
    }

    @EventListener
    @Transactional
    public void createStatistics(TeamCreationEvent event) {
        TeamStatistics teamStatistics = TeamStatistics.builder()
                .team(event.team())
                .build();
        entityManager.persist(teamStatistics);
    }

    @Override
    @Transactional
    public TeamStatisticsReadDto updateStatistics(Long teamId, MatchResultType matchResultType) {
        TeamStatistics teamStatistics = teamStatisticsRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundBaseException(TEAM_STATS_NOT_FOUND.formatted(teamId)));

        teamStatistics.setMatchesPlayed(teamStatistics.getMatchesPlayed() + 1);
        switch (matchResultType) {
            case WIN -> teamStatistics.setWins(teamStatistics.getWins() + 1);
            case LOSE -> teamStatistics.setLosses(teamStatistics.getLosses() + 1);
            case DRAW -> teamStatistics.setDraws(teamStatistics.getDraws() + 1);
        }
        TeamStatistics savedTeamStatistics = teamStatisticsRepository.save(teamStatistics);
        return teamStatisticsMapper.toDto(savedTeamStatistics);
    }

}
