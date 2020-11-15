package com.cpsc304.sprintplanner.persistence.repositories;

import com.cpsc304.sprintplanner.persistence.entities.Team;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Repository
public interface TeamRepository extends CrudRepository<Team, String> {
    @Query(value="SELECT t.teamid, t.orgid, t.logo, t.name, o.orgid as organization_orgid, o.name as organization_name " +
            "FROM TEAM_MEMBERS tm, TEAM t, ORGANIZATION o " +
            "WHERE tm.userid = :userId AND tm.teamid = t.teamid AND o.orgid = t.orgid", nativeQuery = true)
    List<Team> getAllTeams(@Param("userId") UUID userId);

    @Query(value="SELECT t.teamid, t.orgid, t.logo, t.name, o.orgid as organization_orgid, o.name as organization_name " +
            "FROM TEAM t, ORGANIZATION o " +
            "WHERE t.teamid " +
            "IN (SELECT manages FROM MANAGER WHERE userid = :userId) AND t.orgid = o.orgid", nativeQuery = true)
    List<Team> getManagedTeams(@Param("userId") UUID userId);

    @Query(value="INSERT INTO TEAM (orgid, name) VALUES ((SELECT organization from USERS WHERE userid = :userId), :name) " +
            "RETURNING teamid, orgid, logo, name, " +
            "(SELECT organization from USERS WHERE userid = :userId) as organization_orgid, " +
            "(SELECT name from ORGANIZATION o WHERE o.orgid = (SELECT organization from USERS WHERE userid = :userId)) as organization_name", nativeQuery = true)
    Team addTeam(@Param("name") String name, @Param("userId") UUID userId);

    @Query(value="INSERT INTO TEAM_MEMBERS (userid, teamid) " +
            "VALUES (:userId, (SELECT teamid FROM TEAM WHERE name = :name AND orgid = (SELECT organization from USERS WHERE userid = :userId))) " +
            "RETURNING CAST(teamid as VARCHAR)", nativeQuery = true)
    String joinTeam(@Param("name") String name, @Param("userId") UUID userId);

    @Query(value="SELECT t.teamid, t.orgid, t.logo, t.name, o.orgid as organization_orgid, o.name as organization_name " +
            "FROM TEAM t, ORGANIZATION o " +
            "WHERE t.teamid = :id AND t.orgid = o.orgid", nativeQuery = true)
    Team selectTeam(@Param("id") String id);

    @Modifying
    @Transactional
    @Query(value="INSERT INTO MANAGER (manages, userid) VALUES (:teamId, :userId)", nativeQuery = true)
    void addManager(@Param("teamId") UUID teamId, @Param("userId") UUID userId);
}

