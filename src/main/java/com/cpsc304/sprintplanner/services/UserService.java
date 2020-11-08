package com.cpsc304.sprintplanner.services;

import com.cpsc304.sprintplanner.dto.UserDto;
import com.cpsc304.sprintplanner.exceptions.FailedToFindUserException;
import com.cpsc304.sprintplanner.exceptions.FailedToSaveUserException;
import com.cpsc304.sprintplanner.persistence.entities.User;

public interface UserService {
    void saveUser(UserDto user) throws FailedToSaveUserException;
    User getUserByUsername(String username) throws FailedToFindUserException;
}
