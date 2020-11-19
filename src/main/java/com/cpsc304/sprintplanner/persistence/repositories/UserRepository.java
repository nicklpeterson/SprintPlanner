package com.cpsc304.sprintplanner.persistence.repositories;

import com.cpsc304.sprintplanner.persistence.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    @Query(value="SELECT * FROM USERS WHERE USERNAME=:username", nativeQuery=true)
    User findByUsername(@Param("username") String username);

    @Query(value="SELECT username FROM USERS WHERE USERID=:userId", nativeQuery=true)
    String getUsername(@Param("userId") UUID userId);

    @Query(value="SELECT * FROM USERS WHERE USERID=:userId", nativeQuery=true)
    User findByUserId(@Param("userId") UUID userId);

    @Modifying
    @Transactional
    @Query(value=
            "INSERT INTO USERS (userId, username, password, email, organization) " +
            "VALUES (:id, :username, :password, :email, :organization);"
    , nativeQuery=true)
    void saveUser(@Param("id") UUID id,
                  @Param("username") String username,
                  @Param("password") String password,
                  @Param("email") String email,
                  @Param("organization") UUID organization);

    @Modifying
    @Transactional
    @Query(value="INSERT INTO USER_SKILL (userId, skillDescription) VALUES (:userId, :skill)", nativeQuery=true)
    void addSkill(@Param("userId") UUID userId,
                  @Param("skill") String skill);

    @Modifying
    @Transactional
    @Query(value="DELETE FROM USER_SKILL WHERE userId = :userId AND skillDescription = :skill", nativeQuery=true)
    void removeSkill(@Param("userId") UUID userId,
                     @Param("skill") String skill);

    @Query(value="SELECT skillDescription FROM USER_SKILL WHERE userId = :userId", nativeQuery=true)
    List<String> getUserSkills(@Param("userId") UUID userId);

    @Query(value=
            "SELECT PICTURE.img FROM PICTURE WHERE PICTURE.pictureid " +
            "= (SELECT displaypicture FROM USERS WHERE username = :username)", nativeQuery=true)
    byte[] getProfilePic(@Param("username") String username);

    @Query(value="SELECT * FROM PROJECTS p1, USERS u, TEAM_MEMBERS tm WHERE p1.createdby = tm.teamid AND " +
            "u.userid = tm.userid AND p1.projectid = (SELECT DISTINCT(t2.projectid) FROM TICKETS t2\n" +
            "WHERE t2. ticketid =:ticketId)", nativeQuery=true)
    List<User> getAvailableMembers(@Param("ticketId") UUID ticketId);
}
