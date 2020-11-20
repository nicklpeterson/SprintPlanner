package com.cpsc304.sprintplanner.services.impl;

import com.cpsc304.sprintplanner.persistence.entities.Sprint;
import com.cpsc304.sprintplanner.persistence.repositories.SprintRepository;
import com.cpsc304.sprintplanner.services.SprintService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class SprintServiceImpl implements SprintService {
    private SprintRepository sprintRepository;


    @Override
    public List<Sprint> getAllSprints(UUID teamId) throws Exception {
        try {
            List<Tuple> tuples = sprintRepository.getTeamSprints(teamId);
            List<Sprint> sprints = new ArrayList<>();
            for (Tuple tuple : tuples) {
                sprints.add(new Sprint(
                        (Integer) tuple.get("sprintnumber"),
                        (Integer) tuple.get("capacity"),
                        (Timestamp) tuple.get("startdate"),
                        (Timestamp) tuple.get("enddate"),
                        (Integer) tuple.get("sprintload"),
                        UUID.fromString((String) tuple.get("belongsto")),
                        (String) tuple.get("projectName")
                ));
            }
            return sprints;
        } catch (Exception e) {
            log.info(e.getMessage());
            log.info(e.toString());
            throw new Exception(e.getMessage(), e);
        }
    }

    @Override
    public Integer getNumOfUsersWithTickets(Integer sprintNumber) throws Exception {
        try {
            return sprintRepository.getNumOfUsersWithTickets(sprintNumber);
        } catch (Exception e) {
            log.info(e.getMessage());
            log.info(e.toString());
            throw new Exception(e.getMessage(), e);
        }
    }

    @Override
    public Integer getMaxPoints(Integer sprintNumber) throws Exception {
        try {
            return sprintRepository.getMaxPoints(sprintNumber);
        } catch (Exception e) {
            log.info(e.getMessage());
            log.info(e.toString());
            throw new Exception(e.getMessage(), e);
        }
    }

    @Override
    public void deleteSprint(int sprintNumber, UUID projectId) throws Exception {
        try {
            sprintRepository.deleteSprint(sprintNumber, projectId);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception(e.getMessage(), e);
        }
    }
}
