package com.microservices.team.service.impl;

import com.common.exception.exception.base.BadRequestBaseException;
import com.common.exception.exception.base.ResourceNotFoundException;
import com.microservices.team.dto.team.TeamCreateDto;
import com.microservices.team.dto.team.TeamReadDto;
import com.microservices.team.dto.team.TeamUpdateDto;
import com.microservices.team.entity.Team;
import com.microservices.team.event.TeamCreationEvent;
import com.microservices.team.mapper.TeamMapper;
import com.microservices.team.mapper.TeamMemberMapper;
import com.microservices.team.property.ServiceUrlsProperties;
import com.microservices.team.repository.TeamMemberRepository;
import com.microservices.team.repository.TeamRepository;
import com.microservices.team.service.interfaces.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamMemberMapper teamMemberMapper;
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

    @Override
    @Transactional
    public TeamReadDto createTeam(TeamCreateDto teamCreateDto) {
        Team team = teamMapper.toEntity(teamCreateDto);
        eventPublisher.publishEvent(new TeamCreationEvent(team));
        return teamMapper.toDto(teamRepository.save(team));
    }

    public void checkUserExistenceById(Long userId) {
        Optional.ofNullable(userId)
                .ifPresent(id -> restTemplate.getForEntity(serviceUrlsProperties.userServiceUrl() + id, Void.class));
    }

    @Override
    @Transactional
    public TeamReadDto updateTeam(Long id, TeamUpdateDto dto) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TEAM_NOT_FOUND.formatted(id)));

        Long captainId = dto.captainId();
        Long currentCaptainId = team.getCaptainId();
        if (captainId != null) {
            if (currentCaptainId == null || !Objects.equals(currentCaptainId, captainId)) {
                checkUserExistenceById(captainId);
                if (!teamMemberRepository.existsById(teamMemberMapper.toEmbeddedId(id, captainId))) {
                    throw new BadRequestBaseException("User with id [%d] is not a member of the team with id [%d]"
                            .formatted(captainId, id));
                }
                team.setCaptainId(captainId);
            }

        } else if (currentCaptainId != null) {
            team.setCaptainId(null);
        }

        Team updateTeam = teamMapper.updateEntity(dto, team);
        Team savedTeam = teamRepository.saveAndFlush(updateTeam);
        return teamMapper.toDto(savedTeam);
    }

    @Override
    @Transactional
    public void deleteTeam(Long id) {
        teamRepository.delete(findTeamById(id));
    }

}