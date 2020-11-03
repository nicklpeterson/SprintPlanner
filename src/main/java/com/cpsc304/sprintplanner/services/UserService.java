package com.cpsc304.sprintplanner.services;

import com.cpsc304.sprintplanner.dto.UserDto;
import com.cpsc304.sprintplanner.exceptions.FailedToSaveUserException;

public interface UserService {
    void saveUser(UserDto user) throws FailedToSaveUserException;
}
