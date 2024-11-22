package com.microservices.tournament.entity;

import com.microservices.tournament.entity.embedded.TournamentIdAndTeamId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tournament_teams")
public class TournamentTeam {

    @EmbeddedId
    private TournamentIdAndTeamId id;

    @Builder.Default
    @Column(nullable = false, updatable = false)
    private Instant registrationDate = Instant.now();

    @Builder.Default
    private Boolean isApproved = false;
}
