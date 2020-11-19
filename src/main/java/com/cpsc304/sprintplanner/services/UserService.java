package com.cpsc304.sprintplanner.services;

import com.cpsc304.sprintplanner.dto.UserDto;
import com.cpsc304.sprintplanner.exceptions.FailedToFindUserException;
import com.cpsc304.sprintplanner.exceptions.FailedToSaveUserException;
import com.cpsc304.sprintplanner.persistence.entities.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void saveUser(UserDto user) throws FailedToSaveUserException;
    void addSkill(UUID userId, String skill);
    void removeSkill(UUID userId, String skill);
    List<String> getSkills(UUID userId);
    User getUserDetails(UUID userId);
    byte[] getProfilePic(String username);
    String getUsername(UUID userId) throws FailedToFindUserException;
    User getUserByUsername(String username) throws FailedToFindUserException;
}
