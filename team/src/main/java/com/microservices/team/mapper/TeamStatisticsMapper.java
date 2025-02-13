package com.microservices.team.mapper;

import com.microservices.team.dto.statistics.TeamStatisticsReadDto;
import com.microservices.team.entity.TeamStatistics;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TeamStatisticsMapper {

    @Mapping(target = "teamId", source = "team.id")
    TeamStatisticsReadDto toDto(TeamStatistics entity);

}
