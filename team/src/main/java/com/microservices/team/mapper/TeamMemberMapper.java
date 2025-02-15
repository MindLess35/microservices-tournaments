package com.microservices.team.mapper;

import com.microservices.team.dto.member.TeamMemberCreateDto;
import com.microservices.team.dto.member.TeamMemberReadDto;
import com.microservices.team.dto.member.TeamMemberUpdateDto;
import com.microservices.team.entity.TeamMember;
import com.microservices.team.entity.embedded.TeamIdAndUserId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TeamMemberMapper {

    @Mapping(target = "id.teamId", source = "teamId")
    @Mapping(target = "id.userId", source = "userId")
    TeamMember toEntity(TeamMemberCreateDto dto);

    TeamIdAndUserId toEmbeddedId(Long teamId, Long userId);

    TeamMember updateEntity(TeamMemberUpdateDto dto, @MappingTarget TeamMember teamMember);

    @Mapping(target = "teamId", source = "id.teamId")
    @Mapping(target = "userId", source = "id.userId")
    TeamMemberReadDto toDto(TeamMember teamMember);
}
