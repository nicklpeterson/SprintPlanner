package com.cpsc304.sprintplanner.persistence.repositories;

import com.cpsc304.sprintplanner.persistence.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    @Query(value="SELECT * FROM USERS WHERE USERNAME=:username", nativeQuery=true)
    User findByUsername(@Param("username") String username);
}
