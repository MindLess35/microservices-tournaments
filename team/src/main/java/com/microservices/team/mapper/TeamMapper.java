package com.microservices.team.mapper;

import com.microservices.team.dto.TeamCreateDto;
import com.microservices.team.dto.TeamReadDto;
import com.microservices.team.dto.TeamUpdateDto;
import com.microservices.team.entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TeamMapper {

    Team toEntity(TeamCreateDto dto);

    Team updateEntity(TeamUpdateDto teamUpdateDto, @MappingTarget Team user);

    TeamReadDto toDto(Team user);

}
