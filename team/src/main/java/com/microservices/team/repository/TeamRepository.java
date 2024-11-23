package com.microservices.team.repository;

import com.microservices.team.entity.Team;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    @Query("""
            SELECT t
            FROM Team t
            WHERE t.id IN :ids
            """)
    List<Team> findTeamsByIds(List<Long> ids, Pageable pageable);
}

