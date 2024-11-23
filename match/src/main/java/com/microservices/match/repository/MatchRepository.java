package com.microservices.match.repository;

import com.microservices.match.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MatchRepository extends JpaRepository<Match, Long> {
    @Query("""
            SELECT COUNT(m.id) = 1
            FROM Match m
            WHERE m.firstTeamId = :firstTeamId
                  AND m.secondTeamId = :secondTeamId
                  AND m.tournamentId = :tournamentId
            """)
    boolean existsBy(Long firstTeamId, Long secondTeamId, Long tournamentId);

}
