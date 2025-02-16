package com.microservices.match.client;

import com.microservices.match.config.TournamentFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "tournament", configuration = TournamentFeignConfig.class)
public interface TournamentFeignClient {

    @GetMapping("/api/v1/tournaments/{id}")
    ResponseEntity<Void> checkTournamentExistence(@PathVariable Long id);

}