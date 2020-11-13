package com.cpsc304.sprintplanner.services;

import com.cpsc304.sprintplanner.persistence.entities.User;
import java.util.List;
import java.util.UUID;

public interface TeamMemberService {
    List<User> getAllTeamMembers(UUID teamId) throws Exception;
}
