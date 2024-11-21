package com.microservices.team.repository;

import com.microservices.team.entity.TeamStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamStatisticsRepository extends JpaRepository<TeamStatistics, Long> {
}

