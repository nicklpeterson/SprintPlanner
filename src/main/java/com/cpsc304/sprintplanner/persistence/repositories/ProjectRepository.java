package com.cpsc304.sprintplanner.persistence.repositories;

import com.cpsc304.sprintplanner.persistence.entities.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends CrudRepository<Project, UUID> {
    // Join
    @Query(value = "SELECT p.projectId as project_id, p.projectname as projectname, p.createdby as created_by FROM projects p, team t, team_members tm WHERE p.createdBy=t.teamId AND tm.teamId=t.teamId AND tm.userId=:userId", nativeQuery = true)
    List<Project> getAllProjectsByUserId(@Param("userId") UUID userId);
}
