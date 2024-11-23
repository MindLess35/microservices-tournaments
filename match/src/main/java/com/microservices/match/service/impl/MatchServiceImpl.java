package com.microservices.match.service.impl;

import com.common.exception.exception.base.BadRequestBaseException;
import com.common.exception.exception.base.NotFoundBaseException;
import com.microservices.match.dto.MatchCreateDto;
import com.microservices.match.dto.MatchReadDto;
import com.microservices.match.dto.MatchUpdateDto;
import com.microservices.match.entity.Match;
import com.microservices.match.client.TeamFeignClient;
import com.microservices.match.client.TournamentFeignClient;
import com.microservices.match.mapper.MatchMapper;
import com.microservices.match.repository.MatchRepository;
import com.microservices.match.service.interfaces.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final MatchMapper matchMapper;
    private final TournamentFeignClient tournamentClient;
    private final TeamFeignClient teamClient;
    private static final String MATCH_NOT_FOUND = "Match with id [%d] not found";

    @Override
    public MatchReadDto findById(Long id) {
        return matchRepository.findById(id)
                .map(matchMapper::toDto)
                .orElseThrow(() -> new NotFoundBaseException(MATCH_NOT_FOUND.formatted(id)));
    }

    @Override
    @Transactional
    public MatchReadDto createMatch(MatchCreateDto dto) {
        swapTeamIdsIfNecessary(dto);
        Long firstTeamId = dto.getFirstTeamId();
        Long secondTeamId = dto.getSecondTeamId();
        if (firstTeamId.equals(secondTeamId)) {
            throw new BadRequestBaseException("Team with id [%d] cannot play a match against itself".formatted(firstTeamId));
        }
        Long tournamentId = dto.getTournamentId();
        if (matchRepository.existsBy(firstTeamId, secondTeamId, tournamentId)) {
            throw new BadRequestBaseException("Match with firstTeamId [%d], secondTeamId [%d] and tournamentId [%d] is already exists"
                    .formatted(firstTeamId, secondTeamId, tournamentId));
        }

        tournamentClient.checkTournamentExistence(tournamentId);
        teamClient.checkTeamsExistence(firstTeamId, secondTeamId);
        Match match = matchMapper.toEntity(dto);
        return matchMapper.toDto(matchRepository.save(match));
    }

    private void swapTeamIdsIfNecessary(MatchCreateDto dto) {
        Long firstTeamId = dto.getFirstTeamId();
        Long secondTeamId = dto.getSecondTeamId();
        if (firstTeamId < secondTeamId) {
            dto.setFirstTeamId(secondTeamId);
            dto.setSecondTeamId(firstTeamId);
        }
    }

    @Override
    @Transactional
    public MatchReadDto updateMatch(Long id, MatchUpdateDto dto) {
        return matchRepository.findById(id)
                .map(match -> matchMapper.updateEntity(dto, match))
                .map(matchRepository::save)
                .map(matchMapper::toDto)
                .orElseThrow(() -> new NotFoundBaseException(MATCH_NOT_FOUND.formatted(id)));
    }

    @Override
    @Transactional
    public void deleteMatch(Long id) {
        matchRepository.delete(matchRepository.findById(id)
                .orElseThrow(() -> new NotFoundBaseException(MATCH_NOT_FOUND.formatted(id))));
    }
}
