package com.cpsc304.sprintplanner.services.impl;

import com.cpsc304.sprintplanner.dto.TeamDto;
import com.cpsc304.sprintplanner.persistence.entities.Team;
import com.cpsc304.sprintplanner.persistence.repositories.TeamRepository;
import com.cpsc304.sprintplanner.services.TeamService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;

    @Override
    public List<Team> getAllTeams(UUID userId) throws Exception {
        try {
            return sprintLoadHelper(teamRepository.getAllTeams(userId));
        } catch (Exception e) {
            log.info(e.getMessage());
            log.info(e.toString());
            throw new Exception(e.getMessage(), e);
        }
    }

    @Override
    public List<Team> getManagedTeams(UUID userId) throws Exception {
        try {
            return sprintLoadHelper(teamRepository.getManagedTeams(userId));
        } catch (Exception e) {
            log.info(e.getMessage());
            log.info(e.toString());
            throw new Exception(e.getMessage(), e);
        }
    }

    private List<Team> sprintLoadHelper(List<Team> teams) {
        List<Tuple> sprintLoad = teamRepository.teamSprintLoad(
                teams.stream().map(Team::getTeamId).collect(Collectors.toList())
        );
        HashMap<UUID, BigInteger> teamMap = new HashMap<>();
        for (Tuple tuple : sprintLoad) {
            teamMap.put(UUID.fromString((String) tuple.get("createdby")), (BigInteger) tuple.get("sum"));
        }
        for (Team team : teams) {
            if (teamMap.containsKey(team.getTeamId())) {
                team.setSprintLoad(teamMap.get(team.getTeamId()));
            } else {
                team.setSprintLoad(BigInteger.valueOf(0));
            }
        }
        return teams;
    }

    @Override
    public Team addTeam(TeamDto team, UUID userId) {
        Team t = teamRepository.addTeam(team.getName(), userId);
        teamRepository.addManager(t.getTeamId(), userId);
        return t;
    }

    @Override
    public Team joinTeam(TeamDto team, UUID userId) {
        String id = teamRepository.joinTeam(team.getName(), userId);
        Team t = teamRepository.selectTeam(id);
        t.setSprintLoad(teamRepository.singleTeamSprintLoad(t.getTeamId()));
        return t;
    }
}
