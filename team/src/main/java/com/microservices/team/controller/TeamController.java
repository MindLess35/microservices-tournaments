package com.microservices.team.controller;

import com.microservices.team.dto.team.TeamCreateDto;
import com.microservices.team.dto.team.TeamReadDto;
import com.microservices.team.dto.team.TeamUpdateDto;
import com.microservices.team.service.interfaces.TeamService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teams")
public class TeamController {
    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<TeamReadDto> createTeam(@RequestBody @Validated TeamCreateDto teamCreateDto) {
        return ResponseEntity.status(CREATED).body(teamService.createTeam(teamCreateDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamReadDto> getTeam(@PathVariable("id") Long id) {
        return ResponseEntity.ok(teamService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Void> checkTeamsExists(@RequestParam Long firstTeamId, @RequestParam Long secondTeamId) {
        teamService.checkTeamsExists(firstTeamId, secondTeamId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamReadDto> updateTeam(@PathVariable("id") Long id,
                                                  @RequestBody @Validated TeamUpdateDto teamUpdateDto) {
        return ResponseEntity.ok(teamService.updateTeam(id, teamUpdateDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteTeam(@PathVariable("id") Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }

}