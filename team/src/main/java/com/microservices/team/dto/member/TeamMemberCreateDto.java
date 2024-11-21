package com.microservices.team.dto.member;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record TeamMemberCreateDto(
        @NotNull
        @Positive
        Long teamId,

        @NotNull
        @Positive
        Long userId,

        @Size(max = 128)
        String role) {
}
