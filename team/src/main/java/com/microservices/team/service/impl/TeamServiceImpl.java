package com.microservices.team.service.impl;

import com.common.exception.exception.base.ResourceNotFoundException;
import com.microservices.team.dto.TeamCreateDto;
import com.microservices.team.dto.TeamReadDto;
import com.microservices.team.dto.TeamUpdateDto;
import com.microservices.team.mapper.TeamMapper;
import com.microservices.team.repository.TeamRepository;
import com.microservices.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private static final String TEAM_NOT_FOUND = "Team with id [%d] not found";

    @SuppressWarnings({"OptionalGetWithoutIsPresent", "java:S3655"})
    @Override
    @Transactional
    public TeamReadDto createTeam(TeamCreateDto teamCreateDto) {
        return Optional.of(teamCreateDto)
                .map(teamMapper::toEntity)
                .map(teamRepository::save)
                .map(teamMapper::toDto)
                .get();
    }

    @Override
    public TeamReadDto findById(Long id) {
        return teamMapper.toDto(teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TEAM_NOT_FOUND.formatted(id))));
    }

    @Override
    @Transactional
    public TeamReadDto updateTeam(Long id, TeamUpdateDto dto) {
        return teamRepository.findById(id)
                .map(u -> teamMapper.updateEntity(dto, u))
                .map(teamRepository::save)
                .map(teamMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(TEAM_NOT_FOUND.formatted(id)));
    }

    @Override
    @Transactional
    public void deleteTeam(Long id) {
        teamRepository.delete(teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TEAM_NOT_FOUND.formatted(id))));
    }

}