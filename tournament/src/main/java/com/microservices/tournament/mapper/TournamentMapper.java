package com.microservices.tournament.mapper;

import com.microservices.tournament.dto.tournament.TournamentCreateDto;
import com.microservices.tournament.dto.tournament.TournamentReadDto;
import com.microservices.tournament.dto.tournament.TournamentUpdateDto;
import com.microservices.tournament.entity.Tournament;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TournamentMapper {

    Tournament toEntity(TournamentCreateDto dto);

    Tournament updateEntity(TournamentUpdateDto dto, @MappingTarget Tournament tournament);

    TournamentReadDto toDto(Tournament entity);
}
