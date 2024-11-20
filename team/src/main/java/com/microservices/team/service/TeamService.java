package com.microservices.team.service;


import com.microservices.team.dto.TeamCreateDto;
import com.microservices.team.dto.TeamReadDto;
import com.microservices.team.dto.TeamUpdateDto;

public interface TeamService {

    TeamReadDto updateTeam(Long id, TeamUpdateDto teamUpdateDto);

    void deleteTeam(Long id);

    TeamReadDto findById(Long id);

    TeamReadDto createTeam(TeamCreateDto teamCreateDto);
}