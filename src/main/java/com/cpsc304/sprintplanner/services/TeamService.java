package com.cpsc304.sprintplanner.services;

import com.cpsc304.sprintplanner.dto.TeamDto;
import com.cpsc304.sprintplanner.persistence.entities.Team;

import java.util.List;
import java.util.UUID;

public interface TeamService {
    List<Team> getAllTeams(UUID userId) throws Exception;

    List<Team> getManagedTeams(UUID userId);

    Team addTeam(TeamDto team, UUID userId);

    Team joinTeam(TeamDto team, UUID userId);
}
