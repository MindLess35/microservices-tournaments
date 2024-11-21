package com.microservices.team.controller;

import com.microservices.team.dto.statistics.TeamStatisticsReadDto;
import com.microservices.team.enums.MatchResultType;
import com.microservices.team.service.interfaces.TeamStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teams/{teamId}/statistics")
public class TeamStatisticsController {
    private final TeamStatisticsService teamStatisticsService;

    @GetMapping
    public ResponseEntity<TeamStatisticsReadDto> getStatistics(@PathVariable Long teamId) {
        return ResponseEntity.ok(teamStatisticsService.findById(teamId));
    }

    @PatchMapping
    public ResponseEntity<TeamStatisticsReadDto> updateStatistics(@PathVariable Long teamId,
                                                                  @RequestParam MatchResultType matchResultType) {
        return ResponseEntity.ok(teamStatisticsService.updateStatistics(teamId, matchResultType));
    }

}
