package com.microservices.team.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "team_statistics")
public class TeamStatistics {

    @Id
    @Column(name = "team_id")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Team team;

    @Builder.Default
    private Integer matchesPlayed = 0;

    @Builder.Default
    private Integer wins = 0;

    @Builder.Default
    private Integer losses = 0;

    @Builder.Default
    private Integer draws = 0;
}
