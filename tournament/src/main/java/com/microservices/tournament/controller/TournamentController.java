package com.microservices.tournament.controller;

import com.microservices.tournament.dto.tournament.TournamentCreateDto;
import com.microservices.tournament.dto.tournament.TournamentReadDto;
import com.microservices.tournament.dto.tournament.TournamentUpdateDto;
import com.microservices.tournament.service.interfaces.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tournaments")
public class TournamentController {
    private final TournamentService tournamentService;

    @PostMapping
    public ResponseEntity<TournamentReadDto> createTournament(@RequestBody @Validated TournamentCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tournamentService.createTournament(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentReadDto> getTournament(@PathVariable Long id) {
        return ResponseEntity.ok(tournamentService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TournamentReadDto> updateTournament(@PathVariable Long id,
                                                              @RequestBody @Validated TournamentUpdateDto dto) {
        return ResponseEntity.ok(tournamentService.updateTournament(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTournament(@PathVariable Long id) {
        tournamentService.deleteTournament(id);
        return ResponseEntity.noContent().build();
    }
}
