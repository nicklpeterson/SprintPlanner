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
    @Query(value="SELECT * FROM SPRINTS s, PROJECTS p WHERE s.belongsTo = p.projectId AND p.createdBy=:teamId", nativeQuery = true)
    List<Sprint> getTeamSprints(@Param("teamId") UUID teamId);

    @Query(value="SELECT COUNT(DISTINCT t1.assigneeid) FROM TICKETS t1 WHERE t1.assigneeid IN " +
            "(SELECT t2.assigneeid FROM TICKETS t2 WHERE t2.sprintnumber= 1 AND t2.sprintnumber = t1.sprintnumber " +
            "GROUP BY t2.assigneeid HAVING COALESCE(SUM(t2.points), 0) > 0)", nativeQuery = true)
    Integer getNumOfUsersWithTickets(@Param("sprintNumber") Integer sprintNumber);

    @Query(value="SELECT coalesce(AVG(points), 0) FROM TICKETS WHERE sprintnumber=:sprintNumber GROUP BY assigneeid", nativeQuery = true)
    Double getAvgPoints(@Param("sprintNumber") Integer sprintNumber);


}

