package com.microservices.match.client;

import com.microservices.match.config.TeamFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "team", configuration = TeamFeignConfig.class, url = "http://localhost:8010")
public interface TeamFeignClient {

    @GetMapping("/api/v1/teams")
    ResponseEntity<Void> checkTeamsExistence(@RequestParam Long firstTeamId, @RequestParam Long secondTeamId);

}