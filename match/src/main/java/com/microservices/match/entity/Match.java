package com.microservices.match.entity;

import com.microservices.match.enums.MatchResult;
import com.microservices.match.enums.MatchStatus;
import com.microservices.match.enums.SportType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
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
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SportType sportType;

    private Instant startDate;

    private Instant endDate;

    private String location;

    @Enumerated(EnumType.STRING)
    private MatchResult result;

    private Integer scoreFirstTeam;

    private Integer scoreSecondTeam;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private MatchStatus status = MatchStatus.SCHEDULED;

    private Long tournamentId;

    private Long firstTeamId;

    private Long secondTeamId;
}