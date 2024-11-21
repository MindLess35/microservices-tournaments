package com.microservices.team.dto.member;

import java.time.Instant;

public record TeamMemberReadDto(
        Long teamId,
        Long userId,
        String role,
        Instant joinedAt) {
}
