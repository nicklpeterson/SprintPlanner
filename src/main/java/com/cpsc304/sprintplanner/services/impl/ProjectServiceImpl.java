package com.cpsc304.sprintplanner.services.impl;

import com.cpsc304.sprintplanner.exceptions.FailedToFetchProjectsException;
import com.cpsc304.sprintplanner.persistence.entities.Project;
import com.cpsc304.sprintplanner.persistence.repositories.ProjectRepository;
import com.cpsc304.sprintplanner.services.ProjectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    @Override
    public List<Project> getProjectsByUserId(UUID userId) throws FailedToFetchProjectsException {
        try {
            return projectRepository.getAllProjectsByUserId(userId);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new FailedToFetchProjectsException(e.getMessage(), e);
        }
    }
}
