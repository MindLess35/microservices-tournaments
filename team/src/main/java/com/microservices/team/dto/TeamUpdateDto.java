package com.microservices.team.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record TeamUpdateDto(
        @Size(max = 64)
        String name,

        @Size(max = 512)
        String about,

        @Positive
        Long captainId) {
}
