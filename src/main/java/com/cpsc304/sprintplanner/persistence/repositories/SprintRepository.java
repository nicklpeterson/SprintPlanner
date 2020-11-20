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
    @Query(value="SELECT s.* " +
            "FROM sprints s, projects p, team t " +
            "WHERE s.belongsto = p.projectid " +
            "and t.teamid = p.createdby " +
            "and t.teamid = :teamId", nativeQuery = true)
    List<Sprint> getTeamSprints(@Param("teamId") UUID teamId);

    @Query(value="SELECT COUNT(DISTINCT t1.assigneeid) FROM TICKETS t1 WHERE t1.assigneeid IN " +
            "(SELECT t2.assigneeid FROM TICKETS t2 WHERE t2.sprintnumber =:sprintNumber AND t2.sprintnumber = t1.sprintnumber " +
            "GROUP BY t2.assigneeid HAVING COALESCE(SUM(t2.points), 0) > 0)", nativeQuery = true)
    Integer getNumOfUsersWithTickets(@Param("sprintNumber") Integer sprintNumber);

    @Query(value="SELECT SUM(t.points)\n" +
            "FROM tickets t, users u\n" +
            "WHERE u.userid = t.assigneeid AND sprintnumber=:sprintNumber\n" +
            "GROUP BY u.userid\n" +
            "HAVING sum(t.points) >= ALL \n" +
            "    (SELECT coalesce(SUM(t2.points), 0) \n" +
            "    FROM tickets t2, users u2\n" +
            "    WHERE u2.userid = t2.assigneeid AND sprintnumber=:sprintNumber\n" +
            "    GROUP BY u2.userId)", nativeQuery = true)
    Integer getMaxPoints(@Param("sprintNumber") Integer sprintNumber);

}

