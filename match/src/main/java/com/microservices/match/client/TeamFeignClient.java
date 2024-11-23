package com.microservices.match.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "team")
public interface TeamFeignClient {

    @GetMapping("/api/v1/teams")
    ResponseEntity<Void> checkTeamsExistence(@RequestParam Long firstTeamId, @RequestParam Long secondTeamId);

}
