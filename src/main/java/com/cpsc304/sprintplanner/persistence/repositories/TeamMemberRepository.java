package com.cpsc304.sprintplanner.persistence.repositories;
import com.cpsc304.sprintplanner.persistence.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface TeamMemberRepository extends CrudRepository<User, String> {
    // TODO: FOR SOME REASON I CAN'T JUST SELECT THE USERID, I have to select all
    // MAY HAVE TO CREATE A NEW DATA TYPE TO DEAL WITH THIS
    @Query(value="SELECT * FROM team_members tm, users u WHERE tm.teamid = :tId AND tm.userid = u.userid", nativeQuery = true)
    List<User> getAllTeamMembers(@Param("tId") UUID tId);
}

