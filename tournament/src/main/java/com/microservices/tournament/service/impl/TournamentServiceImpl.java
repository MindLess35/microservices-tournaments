package com.microservices.tournament.service.impl;

import com.common.exception.exception.base.NotFoundBaseException;
import com.microservices.tournament.dto.tournament.TournamentCreateDto;
import com.microservices.tournament.dto.tournament.TournamentReadDto;
import com.microservices.tournament.dto.tournament.TournamentUpdateDto;
import com.microservices.tournament.entity.Tournament;
import com.microservices.tournament.mapper.TournamentMapper;
import com.microservices.tournament.repository.TournamentRepository;
import com.microservices.tournament.service.interfaces.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TournamentServiceImpl implements TournamentService {
    private final TournamentRepository tournamentRepository;
    private final TournamentMapper tournamentMapper;
    private static final String TOURNAMENT_NOT_FOUND = "Tournament with id [%d] not found";

    @Override
    public TournamentReadDto findById(Long id) {
        return tournamentMapper.toDto(
                tournamentRepository.findById(id)
                        .orElseThrow(() -> new NotFoundBaseException(TOURNAMENT_NOT_FOUND.formatted(id)))
        );
    }

    @Override
    @Transactional
    public TournamentReadDto createTournament(TournamentCreateDto dto) {
        Tournament tournament = tournamentMapper.toEntity(dto);
        return tournamentMapper.toDto(tournamentRepository.save(tournament));
    }

    @Override
    @Transactional
    public TournamentReadDto updateTournament(Long id, TournamentUpdateDto dto) {
        return tournamentRepository.findById(id)
                .map(tournament -> tournamentMapper.updateEntity(dto, tournament))
                .map(tournamentRepository::saveAndFlush)
                .map(tournamentMapper::toDto)
                .orElseThrow(() -> new NotFoundBaseException(TOURNAMENT_NOT_FOUND.formatted(id)));
    }

    @Override
    @Transactional
    public void deleteTournament(Long id) {
        Tournament tournament = tournamentRepository.findById(id)
                .orElseThrow(() -> new NotFoundBaseException(TOURNAMENT_NOT_FOUND.formatted(id)));
        tournamentRepository.delete(tournament);
    }


}
