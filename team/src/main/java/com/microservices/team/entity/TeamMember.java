package com.microservices.team.entity;

import com.microservices.team.entity.embedded.TeamIdAndUserId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Setter
@Getter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "team_members")
public class TeamMember {

    @EmbeddedId
    private TeamIdAndUserId id;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    private Team team;
//
////  @ManyToOne(fetch = FetchType.LAZY)
//    private Long userId;

    private String role;

    @Builder.Default
    @Column(updatable = false, nullable = false)
    private Instant joinedAt = Instant.now();
}
