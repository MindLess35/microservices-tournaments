package com.microservices.tournament.service.impl;

import com.common.exception.exception.base.BadRequestBaseException;
import com.common.exception.exception.base.ResourceNotFoundException;
import com.microservices.tournament.dto.team.TournamentTeamCreateDto;
import com.microservices.tournament.dto.team.TournamentTeamReadDto;
import com.microservices.tournament.dto.team.TournamentTeamUpdateDto;
import com.microservices.tournament.entity.TournamentTeam;
import com.microservices.tournament.entity.embedded.TournamentIdAndTeamId;
import com.microservices.tournament.mapper.TournamentTeamMapper;
import com.microservices.tournament.property.ServiceUrlsProperties;
import com.microservices.tournament.repository.TournamentTeamRepository;
import com.microservices.tournament.service.interfaces.TournamentService;
import com.microservices.tournament.service.interfaces.TournamentTeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TournamentTeamServiceImpl implements TournamentTeamService {
    private final TournamentTeamRepository tournamentTeamRepository;
    private final TournamentService tournamentService;
    private final TournamentTeamMapper tournamentTeamMapper;
    private final RestClient.Builder restClientBuilder;
    private final ServiceUrlsProperties serviceUrlsProperties;
    public static final String TOURNAMENT_TEAM_NOT_FOUND = "Tournament team with id [%s] not found";

    @Override
    public TournamentTeamReadDto findById(TournamentIdAndTeamId id) {
        return tournamentTeamMapper.toDto(
                tournamentTeamRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(TOURNAMENT_TEAM_NOT_FOUND.formatted(id)))
        );
    }

    @Override
    @Transactional
    public TournamentTeamReadDto createTournamentTeam(TournamentTeamCreateDto dto) {
        Long tournamentId = dto.tournamentId();
        Long teamId = dto.teamId();
        if (tournamentTeamRepository.existsById(tournamentTeamMapper.toEmbeddedId(dto))) {
            throw new BadRequestBaseException("Tournament team with teamId [%d] and tournamentId [%d] is already exists"
                    .formatted(teamId, tournamentId));
        }
        tournamentService.findById(tournamentId);
        checkTeamExistenceById(teamId);
        TournamentTeam tournamentTeam = tournamentTeamMapper.toEntity(dto);
        return tournamentTeamMapper.toDto(tournamentTeamRepository.save(tournamentTeam));
    }

    private void checkTeamExistenceById(Long teamId) {
        restClientBuilder.build()
                .get()
                .uri(serviceUrlsProperties.teamServiceUrl() + teamId)
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    @Transactional
    public TournamentTeamReadDto updateTournamentTeam(TournamentIdAndTeamId id, TournamentTeamUpdateDto dto) {
        return tournamentTeamRepository.findById(id)
                .map(tournamentTeam -> tournamentTeamMapper.updateEntity(dto, tournamentTeam))
                .map(tournamentTeamRepository::saveAndFlush)
                .map(tournamentTeamMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(TOURNAMENT_TEAM_NOT_FOUND.formatted(id)));
    }

    @Override
    @Transactional
    public void deleteTournamentTeam(TournamentIdAndTeamId id) {
        TournamentTeam tournamentTeam = tournamentTeamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TOURNAMENT_TEAM_NOT_FOUND.formatted(id)));
        tournamentTeamRepository.delete(tournamentTeam);
    }
}


