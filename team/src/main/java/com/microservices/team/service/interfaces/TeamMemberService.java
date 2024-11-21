package com.microservices.team.service.interfaces;

import com.microservices.team.entity.embedded.TeamIdAndUserId;
import com.microservices.team.dto.member.TeamMemberCreateDto;
import com.microservices.team.dto.member.TeamMemberReadDto;
import com.microservices.team.dto.member.TeamMemberUpdateDto;

public interface TeamMemberService {

    TeamMemberReadDto createTeamMember(TeamMemberCreateDto createDto);

    TeamMemberReadDto findById(TeamIdAndUserId id);

    TeamMemberReadDto updateTeamMember(TeamIdAndUserId id, TeamMemberUpdateDto updateDto);

    void deleteTeamMember(TeamIdAndUserId id);
}
