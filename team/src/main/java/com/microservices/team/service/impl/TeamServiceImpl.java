package com.microservices.team.service.impl;

import com.common.exception.exception.base.ResourceNotFoundException;
import com.microservices.team.dto.team.TeamCreateDto;
import com.microservices.team.dto.team.TeamReadDto;
import com.microservices.team.dto.team.TeamUpdateDto;
import com.microservices.team.entity.Team;
import com.microservices.team.event.TeamCreationEvent;
import com.microservices.team.event.TeamDeletionEvent;
import com.microservices.team.mapper.TeamMapper;
import com.microservices.team.property.ServiceUrlsProperties;
import com.microservices.team.repository.TeamRepository;
import com.microservices.team.service.interfaces.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final RestTemplate restTemplate;
    private final ServiceUrlsProperties serviceUrlsProperties;
    private final ApplicationEventPublisher eventPublisher;
    private static final String TEAM_NOT_FOUND = "Team with id [%d] not found";

    @Override
    public TeamReadDto findById(Long id) {
        return teamMapper.toDto(findTeamById(id));
    }

    @Override
    public Team findTeamById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TEAM_NOT_FOUND.formatted(id)));
    }

    @Override
    public void checkExistenceById(Long id) {
        boolean isExists = teamRepository.existsById(id);
        if (!isExists)
            throw new ResourceNotFoundException(TEAM_NOT_FOUND.formatted(id));
    }

    @SuppressWarnings({"OptionalGetWithoutIsPresent", "java:S3655"})
    @Override
    @Transactional //todo remove captainId from dto
    public TeamReadDto createTeam(TeamCreateDto teamCreateDto) {
        checkUserExistence(teamCreateDto.captainId());
        Team team = teamMapper.toEntity(teamCreateDto);
        eventPublisher.publishEvent(new TeamCreationEvent(team));
        return teamMapper.toDto(teamRepository.save(team));
    }

    public void checkUserExistence(Long userId) {
        Optional.ofNullable(userId)
                .ifPresent(id -> restTemplate.getForEntity(serviceUrlsProperties.userServiceUrl() + id, Void.class));
    }

    @Override
    @Transactional
    public TeamReadDto updateTeam(Long id, TeamUpdateDto dto) {
        checkUserExistence(dto.captainId());
        return teamRepository.findById(id)
                .map(u -> teamMapper.updateEntity(dto, u))
                .map(teamRepository::save)
                .map(teamMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(TEAM_NOT_FOUND.formatted(id)));
    }

    @Override
    @Transactional
    public void deleteTeam(Long id) {
        eventPublisher.publishEvent(new TeamDeletionEvent(id));
        teamRepository.delete(teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TEAM_NOT_FOUND.formatted(id))));
    }

}