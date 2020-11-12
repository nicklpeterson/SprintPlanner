package com.cpsc304.sprintplanner.persistence.repositories;
import com.cpsc304.sprintplanner.persistence.entities.Sprint;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface SprintRepository extends CrudRepository<Sprint, String> {
    @Query(value="SELECT * FROM SPRINTS s1 WHERE NOT EXISTS (SELECT * FROM SPRINTS s2, PROJECTS p1 WHERE NOT EXISTS \n" +
            "(SELECT * FROM SPRINTS s3, PROJECTS p2 WHERE p1.projectId = p2.projectId AND s2.sprintNumber = s3.sprintNumber AND \n" +
            "s3.belongsTo=p2.projectId AND p2.createdBy= :teamId))", nativeQuery = true)
    List<Sprint> getTeamSprints(@Param("teamId") UUID teamId);

    @Query(value="SELECT COUNT(assigneeId) FROM TICKETS WHERE sprintnumber=:sprintNumber GROUP BY assigneeid HAVING COALESCE(SUM(points), 0) > 0", nativeQuery = true)
    Integer getNumOfUsersWithTickets(@Param("sprintNumber") Integer sprintNumber);

    @Query(value="SELECT coalesce(AVG(points), 0) FROM TICKETS WHERE sprintnumber=:sprintNumber GROUP BY assigneeid", nativeQuery = true)
    Double getAvgPoints(@Param("sprintNumber") Integer sprintNumber);


}

