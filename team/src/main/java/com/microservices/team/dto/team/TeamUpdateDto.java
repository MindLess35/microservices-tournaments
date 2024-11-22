package com.microservices.team.dto.team;

import com.microservices.team.enums.SportType;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record TeamUpdateDto(
        @Size(max = 64)
        String name,

        @Size(max = 512)
        String about,

        SportType sportType,

        @Positive
        Long captainId) {
}
