package com.microservices.match.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "tournament")
public interface TournamentFeignClient {

    @GetMapping("/api/v1/tournaments/{id}")
    ResponseEntity<Void> checkTournamentExistence(@PathVariable Long id);
}