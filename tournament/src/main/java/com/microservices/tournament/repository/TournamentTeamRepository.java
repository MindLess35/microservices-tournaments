package com.microservices.tournament.repository;

import com.microservices.tournament.entity.TournamentTeam;
import com.microservices.tournament.entity.embedded.TournamentIdAndTeamId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentTeamRepository extends JpaRepository<TournamentTeam, TournamentIdAndTeamId> {
}
