package com.microservices.match.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "tournament-service", url = "${http.tournament-service-url}")
public interface TournamentFeignClient {

    @GetMapping("/{id}")
    ResponseEntity<Void> getTournament(@PathVariable Long id);
}