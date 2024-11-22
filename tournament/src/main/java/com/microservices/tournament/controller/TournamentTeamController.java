package com.microservices.tournament.controller;

import com.microservices.tournament.dto.team.TournamentTeamCreateDto;
import com.microservices.tournament.dto.team.TournamentTeamReadDto;
import com.microservices.tournament.dto.team.TournamentTeamUpdateDto;
import com.microservices.tournament.entity.embedded.TournamentIdAndTeamId;
import com.microservices.tournament.service.interfaces.TournamentTeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tournament-teams")
public class TournamentTeamController {
    private final TournamentTeamService tournamentTeamService;

    @GetMapping
    public ResponseEntity<TournamentTeamReadDto> getTournamentTeam(@Validated TournamentIdAndTeamId id) {
        return ResponseEntity.ok(tournamentTeamService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TournamentTeamReadDto> createTournamentTeam(@RequestBody @Validated TournamentTeamCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tournamentTeamService.createTournamentTeam(dto));
    }

    @PutMapping
    public ResponseEntity<TournamentTeamReadDto> updateTournamentTeam(
            @Validated TournamentIdAndTeamId id,
            @RequestBody @Validated TournamentTeamUpdateDto dto
    ) {
        return ResponseEntity.ok(tournamentTeamService.updateTournamentTeam(id, dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTournamentTeam(@Validated TournamentIdAndTeamId id) {
        tournamentTeamService.deleteTournamentTeam(id);
        return ResponseEntity.noContent().build();
    }
}
