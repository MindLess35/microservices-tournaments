package com.microservices.team.service.impl;

import com.common.exception.exception.base.BadRequestBaseException;
import com.common.exception.exception.base.ResourceNotFoundException;
import com.microservices.team.dto.member.TeamMemberCreateDto;
import com.microservices.team.dto.member.TeamMemberReadDto;
import com.microservices.team.dto.member.TeamMemberUpdateDto;
import com.microservices.team.entity.TeamMember;
import com.microservices.team.entity.embedded.TeamIdAndUserId;
import com.microservices.team.mapper.TeamMemberMapper;
import com.microservices.team.repository.TeamMemberRepository;
import com.microservices.team.service.interfaces.TeamMemberService;
import com.microservices.team.service.interfaces.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamMemberServiceImpl implements TeamMemberService {
    private final TeamMemberRepository teamMemberRepository;
    private final TeamMemberMapper teamMemberMapper;
    private final TeamService teamService;
    private static final String TEAM_MEMBER_NOT_FOUND = "Team member with teamId id [%d] and userId [%d] not found";

    @Override
    @Transactional
    public TeamMemberReadDto createTeamMember(TeamMemberCreateDto createDto) {
        Long teamId = createDto.teamId();
        Long userId = createDto.userId();
        teamService.checkExistenceById(teamId);
        if (teamMemberRepository.existsById(teamMemberMapper.toEmbeddedId(createDto))) {
            throw new BadRequestBaseException("User with id [%d] is already a member of the team with id [%d]"
                    .formatted(userId, teamId));
        }
        teamService.checkUserExistence(userId);

        TeamMember teamMember = teamMemberMapper.toEntity(createDto);
        TeamMember save = teamMemberRepository.save(teamMember);
        return teamMemberMapper.toDto(save);
    }

    @Override
    public TeamMemberReadDto findById(TeamIdAndUserId id) {
        return teamMemberRepository.findById(id)
                .map(teamMemberMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(TEAM_MEMBER_NOT_FOUND.formatted(id.teamId(), id.userId())));
    }

    @Override
    @Transactional
    public TeamMemberReadDto updateTeamMember(TeamIdAndUserId id, TeamMemberUpdateDto updateDto) {
        return teamMemberRepository.findById(id)
                .map(member -> teamMemberMapper.updateEntity(updateDto, member))
                .map(teamMemberRepository::saveAndFlush)
                .map(teamMemberMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(TEAM_MEMBER_NOT_FOUND.formatted(id.teamId(), id.userId())));
    }

    @Override
    @Transactional
    public void deleteTeamMember(TeamIdAndUserId id) {
        int deletedCount = teamMemberRepository.deleteTeamMemberById(id);
        if (deletedCount == 0) {
            throw new ResourceNotFoundException(TEAM_MEMBER_NOT_FOUND.formatted(id.teamId(), id.userId()));
        }
    }
}