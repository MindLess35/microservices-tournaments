package com.microservices.tournament.mapper;

import com.microservices.tournament.dto.team.TournamentTeamCreateDto;
import com.microservices.tournament.dto.team.TournamentTeamReadDto;
import com.microservices.tournament.dto.team.TournamentTeamUpdateDto;
import com.microservices.tournament.entity.TournamentTeam;
import com.microservices.tournament.entity.embedded.TournamentIdAndTeamId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TournamentTeamMapper {

    @Mapping(target = "id.tournamentId", source = "tournamentId")
    @Mapping(target = "id.teamId", source = "teamId")
    TournamentTeam toEntity(TournamentTeamCreateDto dto);

    TournamentIdAndTeamId toEmbeddedId(TournamentTeamCreateDto dto);

    @Mapping(target = "tournamentId", source = "id.tournamentId")
    @Mapping(target = "teamId", source = "id.teamId")
    TournamentTeamReadDto toDto(TournamentTeam entity);

    TournamentTeam updateEntity(TournamentTeamUpdateDto dto, @MappingTarget TournamentTeam entity);
}
