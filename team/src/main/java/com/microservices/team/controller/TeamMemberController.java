package com.microservices.team.controller;

import com.microservices.team.entity.embedded.TeamIdAndUserId;
import com.microservices.team.dto.member.TeamMemberCreateDto;
import com.microservices.team.dto.member.TeamMemberReadDto;
import com.microservices.team.dto.member.TeamMemberUpdateDto;
import com.microservices.team.service.interfaces.TeamMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/team-members")
public class TeamMemberController {
    private final TeamMemberService teamMemberService;

    @PostMapping
    public ResponseEntity<TeamMemberReadDto> createTeamMember(@RequestBody @Validated TeamMemberCreateDto createDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(teamMemberService.createTeamMember(createDto));
    }

    @GetMapping
    public ResponseEntity<TeamMemberReadDto> getTeamMember(@Validated TeamIdAndUserId id) {
        return ResponseEntity.ok(teamMemberService.findById(id));
    }

    @PutMapping
    public ResponseEntity<TeamMemberReadDto> updateTeamMember(@Validated TeamIdAndUserId id,
                                                              @RequestBody @Validated TeamMemberUpdateDto updateDto) {
        return ResponseEntity.ok(teamMemberService.updateTeamMember(id, updateDto));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTeamMember(@Validated TeamIdAndUserId id) {
        teamMemberService.deleteTeamMember(id);
        return ResponseEntity.noContent().build();
    }
}
