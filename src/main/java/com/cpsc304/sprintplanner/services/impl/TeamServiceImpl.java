package com.cpsc304.sprintplanner.services.impl;

import com.cpsc304.sprintplanner.persistence.entities.Team;
import com.cpsc304.sprintplanner.persistence.repositories.TeamRepository;
import com.cpsc304.sprintplanner.services.TeamService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;

    @Override
    public List<Team> getAllTeams(UUID userId) throws Exception {
        try {
            return teamRepository.getAllTeams(userId);
        } catch (Exception e) {
            log.info(e.getMessage());
            log.info(e.toString());
            throw new Exception(e.getMessage(), e);
        }
    }
}
