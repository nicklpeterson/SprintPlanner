package com.cpsc304.sprintplanner.persistence.repositories;

import com.cpsc304.sprintplanner.persistence.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    @Query(value="SELECT * FROM USERS WHERE USERNAME=:username", nativeQuery=true)
    User findByUsername(@Param("username") String username);

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
}
