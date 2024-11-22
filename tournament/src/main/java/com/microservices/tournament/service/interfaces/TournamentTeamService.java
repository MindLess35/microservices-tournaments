package com.microservices.tournament.service.interfaces;

import com.microservices.tournament.dto.team.TournamentTeamCreateDto;
import com.microservices.tournament.dto.team.TournamentTeamReadDto;
import com.microservices.tournament.dto.team.TournamentTeamUpdateDto;
import com.microservices.tournament.entity.embedded.TournamentIdAndTeamId;

public interface TournamentTeamService {

    TournamentTeamReadDto findById(TournamentIdAndTeamId id);

    TournamentTeamReadDto createTournamentTeam(TournamentTeamCreateDto dto);

    TournamentTeamReadDto updateTournamentTeam(TournamentIdAndTeamId id, TournamentTeamUpdateDto dto);

    void deleteTournamentTeam(TournamentIdAndTeamId id);
}
