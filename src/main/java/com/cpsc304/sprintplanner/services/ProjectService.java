package com.cpsc304.sprintplanner.services;

import com.cpsc304.sprintplanner.exceptions.FailedToFetchProjectsException;
import com.cpsc304.sprintplanner.persistence.entities.Project;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    List<Project> getProjectsByUserId(UUID userId) throws FailedToFetchProjectsException;
}
