package com.microservices.match.controller;

import com.microservices.match.dto.MatchCreateDto;
import com.microservices.match.dto.MatchReadDto;
import com.microservices.match.dto.MatchUpdateDto;
import com.microservices.match.service.interfaces.MatchService;
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
@RequestMapping("/api/v1/matches")
public class MatchController {
    private final MatchService matchService;

    @PostMapping
    public ResponseEntity<MatchReadDto> createMatch(@RequestBody @Validated MatchCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(matchService.createMatch(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchReadDto> getMatch(@PathVariable Long id) {
        return ResponseEntity.ok(matchService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatchReadDto> updateMatch(@PathVariable Long id,
                                                    @RequestBody @Validated MatchUpdateDto dto) {
        return ResponseEntity.ok(matchService.updateMatch(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        matchService.deleteMatch(id);
        return ResponseEntity.noContent().build();
    }
}
