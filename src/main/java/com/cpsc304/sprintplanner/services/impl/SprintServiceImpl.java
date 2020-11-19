package com.cpsc304.sprintplanner.services.impl;

import com.cpsc304.sprintplanner.persistence.entities.Sprint;
import com.cpsc304.sprintplanner.persistence.repositories.SprintRepository;
import com.cpsc304.sprintplanner.services.SprintService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
            return sprintRepository.getTeamSprints(teamId);
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
}
