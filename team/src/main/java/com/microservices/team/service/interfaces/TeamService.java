package com.microservices.team.service.interfaces;


import com.microservices.team.dto.team.TeamCreateDto;
import com.microservices.team.dto.team.TeamReadDto;
import com.microservices.team.dto.team.TeamUpdateDto;
import com.microservices.team.entity.Team;

public interface TeamService {

    TeamReadDto updateTeam(Long id, TeamUpdateDto teamUpdateDto);

    void deleteTeam(Long id);

    TeamReadDto findById(Long id);

    Team findTeamById(Long id);

    void checkExistenceById(Long id);

    void checkUserExistenceById(Long userId);

    TeamReadDto createTeam(TeamCreateDto teamCreateDto);
}