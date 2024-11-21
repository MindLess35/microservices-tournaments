package com.microservices.team.event;

import com.microservices.team.entity.Team;

public record TeamCreationEvent(Team team) {
}
