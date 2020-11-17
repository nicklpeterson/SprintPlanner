package com.cpsc304.sprintplanner.services;

import com.cpsc304.sprintplanner.persistence.entities.Team;

import java.util.List;
import java.util.UUID;

public interface TeamService {
    List<Team> getAllTeams(UUID userId, UUID orgId) throws Exception;
}
