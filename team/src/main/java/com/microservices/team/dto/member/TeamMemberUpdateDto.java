package com.microservices.team.dto.member;

import jakarta.validation.constraints.Size;

public record TeamMemberUpdateDto(
        @Size(max = 128)
        String role) {
}
