package com.microservices.team.repository;

import com.microservices.team.entity.TeamMember;
import com.microservices.team.entity.embedded.TeamIdAndUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, TeamIdAndUserId> {

    int deleteTeamMemberById(TeamIdAndUserId id);

    boolean existsById(TeamIdAndUserId id);

}
