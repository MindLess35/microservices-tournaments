package com.microservices.match.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "team-service", url = "${http.team-service-url}")
public interface TeamFeignClient {

    @GetMapping("/{id}")
    ResponseEntity<Void> getTeam(@PathVariable("id") Long id);

    @GetMapping
    ResponseEntity<Void> checkTeamsExists(@RequestParam Long firstTeamId, @RequestParam Long secondTeamId);

}