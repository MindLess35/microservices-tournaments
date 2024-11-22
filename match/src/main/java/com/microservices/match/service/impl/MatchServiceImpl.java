package com.microservices.match.service.impl;

import com.common.exception.exception.base.ResourceNotFoundException;
import com.microservices.match.dto.MatchCreateDto;
import com.microservices.match.dto.MatchReadDto;
import com.microservices.match.dto.MatchUpdateDto;
import com.microservices.match.entity.Match;
import com.microservices.match.feignclient.TeamFeignClient;
import com.microservices.match.feignclient.TournamentFeignClient;
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
                .orElseThrow(() -> new ResourceNotFoundException(MATCH_NOT_FOUND.formatted(id)));
    }

    @Override
    @Transactional
    public MatchReadDto createMatch(MatchCreateDto dto) {
        tournamentClient.getTournament(dto.tournamentId());
        teamClient.checkTeamsExists(dto.firstTeamId(), dto.secondTeamId());
        Match match = matchMapper.toEntity(dto);
        return matchMapper.toDto(matchRepository.save(match));
    }

    @Override
    @Transactional
    public MatchReadDto updateMatch(Long id, MatchUpdateDto dto) {
        return matchRepository.findById(id)
                .map(match -> matchMapper.updateEntity(dto, match))
                .map(matchRepository::save)
                .map(matchMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(MATCH_NOT_FOUND.formatted(id)));
    }

    @Override
    @Transactional
    public void deleteMatch(Long id) {
        matchRepository.delete(matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MATCH_NOT_FOUND.formatted(id))));
    }
}
