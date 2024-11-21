package com.microservices.team.entity.embedded;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;

@Embeddable
public record TeamIdAndUserId(
        @NotNull
        @Positive
        Long teamId,

        @NotNull
        @Positive
        Long userId) implements Serializable {
}
