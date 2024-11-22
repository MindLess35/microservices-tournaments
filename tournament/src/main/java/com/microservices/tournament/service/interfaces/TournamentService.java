package com.microservices.tournament.service.interfaces;

import com.microservices.tournament.dto.tournament.TournamentCreateDto;
import com.microservices.tournament.dto.tournament.TournamentReadDto;
import com.microservices.tournament.dto.tournament.TournamentUpdateDto;

public interface TournamentService {

    TournamentReadDto findById(Long id);

    TournamentReadDto createTournament(TournamentCreateDto dto);

    TournamentReadDto updateTournament(Long id, TournamentUpdateDto dto);

    void deleteTournament(Long id);
}
