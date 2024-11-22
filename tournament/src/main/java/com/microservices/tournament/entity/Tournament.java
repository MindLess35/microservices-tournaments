package com.microservices.tournament.entity;

import com.microservices.tournament.enums.SportType;
import com.microservices.tournament.enums.TournamentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tournaments")
public class Tournament extends AuditingEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private SportType sportType;

    private Instant startDate;

    private Instant endDate;

    private String location;

    @Builder.Default
    private BigDecimal prizePool = BigDecimal.ZERO;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private TournamentStatus status = TournamentStatus.PENDING;

}
