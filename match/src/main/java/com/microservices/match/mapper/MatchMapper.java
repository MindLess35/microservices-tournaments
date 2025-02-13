package com.microservices.match.mapper;

import com.microservices.match.dto.MatchCreateDto;
import com.microservices.match.dto.MatchReadDto;
import com.microservices.match.dto.MatchUpdateDto;
import com.microservices.match.entity.Match;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MatchMapper {

    Match toEntity(MatchCreateDto dto);

    @Mapping(target = "id", source = "id")
    MatchReadDto toDto(Match match);

    Match updateEntity(MatchUpdateDto dto, @MappingTarget Match match);
}
