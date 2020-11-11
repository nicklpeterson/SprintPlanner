package com.cpsc304.sprintplanner.services;

import com.cpsc304.sprintplanner.persistence.entities.Sprint;

import java.util.List;
import java.util.UUID;

public interface SprintService {
    List<Sprint> getAllSprints(UUID teamId) throws Exception;
    Integer getNumOfUsersWithTickets(Integer sprintNumber) throws Exception;
    Double getAvgPoints(Integer sprintNumber) throws Exception;
}
