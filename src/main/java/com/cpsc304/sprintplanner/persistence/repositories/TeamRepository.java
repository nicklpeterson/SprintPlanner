package com.cpsc304.sprintplanner.persistence.repositories;
import com.cpsc304.sprintplanner.persistence.entities.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface TeamRepository extends CrudRepository<Team, String> {
    @Query(value="SELECT t.teamid, t.orgid, t.logo, t.name FROM TEAM_MEMBERS tm, TEAM t WHERE tm.userid=:userId AND tm.teamid = t.teamid AND t.orgid =:orgId", nativeQuery = true)
    List<Team> getAllTeams(@Param("userId") UUID userId, @Param("orgId") UUID orgId);
}

