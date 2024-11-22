package com.microservices.team.dto.team;

import com.microservices.team.enums.SportType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record TeamCreateDto(
        @NotBlank
        @Size(max = 64)
        String name,

        @Size(max = 512)
        String about,

        @NotNull
        SportType sportType) {
}