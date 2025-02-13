package com.microservices.team.mapper;

import com.microservices.team.dto.team.TeamCreateDto;
import com.microservices.team.dto.team.TeamReadDto;
import com.microservices.team.dto.team.TeamUpdateDto;
import com.microservices.team.entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TeamMapper {

    Team toEntity(TeamCreateDto dto);

    Team updateEntity(TeamUpdateDto teamUpdateDto, @MappingTarget Team team);

    TeamReadDto toDto(Team team);

}
