package com.cpsc304.sprintplanner.services.impl;

import com.cpsc304.sprintplanner.persistence.entities.User;
import com.cpsc304.sprintplanner.persistence.repositories.TeamMemberRepository;
import com.cpsc304.sprintplanner.services.TeamMemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class TeamMemberServiceImpl implements TeamMemberService {
    private TeamMemberRepository teamMemberRepository;

    @Override
    public List<User> getAllTeamMembers(UUID teamId) throws Exception {
        try {
            return teamMemberRepository.getAllTeamMembers(teamId);
        } catch (Exception e) {
            log.info(e.getMessage());
            log.info(e.toString());
            throw new Exception(e.getMessage(), e);
        }
    }
}
