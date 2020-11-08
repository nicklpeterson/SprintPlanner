package com.cpsc304.sprintplanner.services.impl;

import com.cpsc304.sprintplanner.dto.UserDto;
import com.cpsc304.sprintplanner.exceptions.FailedToFindUserException;
import com.cpsc304.sprintplanner.exceptions.FailedToSaveOrgException;
import com.cpsc304.sprintplanner.exceptions.FailedToSaveUserException;
import com.cpsc304.sprintplanner.persistence.entities.Organization;
import com.cpsc304.sprintplanner.persistence.entities.User;
import com.cpsc304.sprintplanner.persistence.repositories.UserRepository;
import com.cpsc304.sprintplanner.services.OrganizationService;
import com.cpsc304.sprintplanner.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final OrganizationService organizationService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void saveUser(UserDto userDto) throws FailedToSaveUserException {
        try {
            final User user = User.builder()
                    .id(UUID.randomUUID())
                    .username(userDto.getUsername())
                    .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                    .email(userDto.getEmail())
                    .organization(getOrganizationUUID(userDto.getOrganizationName()))
                    .build();
            userRepository.saveUser(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getOrganization());
        } catch (FailedToSaveOrgException e) {
            log.error(e.getMessage());
            throw new FailedToSaveUserException("Failed to save User's org.", e);
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage());
            throw new FailedToSaveUserException("User already exists", e);
        }
    }

    @Override
    public User getUserByUsername(String username) throws FailedToFindUserException {
        final User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new FailedToFindUserException("User with username " + username + " was not found.");
        }
        return user;
    }

    private UUID getOrganizationUUID(String orgName) throws FailedToSaveOrgException {
        Organization organization = organizationService.getOrganizationByName(orgName);
        if (organization == null) {
            organization = new Organization(UUID.randomUUID(), orgName);
            organizationService.saveOrganization(organization);
        }
        return organization.getId();
    }

}
