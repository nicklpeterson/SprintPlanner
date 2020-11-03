package com.cpsc304.sprintplanner.services.impl;

import com.cpsc304.sprintplanner.dto.UserDto;
import com.cpsc304.sprintplanner.exceptions.FailedToSaveOrgException;
import com.cpsc304.sprintplanner.exceptions.FailedToSaveUserException;
import com.cpsc304.sprintplanner.persistence.entities.Employer;
import com.cpsc304.sprintplanner.persistence.entities.Organization;
import com.cpsc304.sprintplanner.persistence.entities.User;
import com.cpsc304.sprintplanner.persistence.repositories.EmployerRepository;
import com.cpsc304.sprintplanner.persistence.repositories.UserRepository;
import com.cpsc304.sprintplanner.services.OrganizationService;
import com.cpsc304.sprintplanner.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.JDBCException;
import org.postgresql.util.PSQLException;
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
    private final EmployerRepository employerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void saveUser(UserDto userDto) throws FailedToSaveUserException {
        // addUserToEmployer(userDto.getEmailDomain(), userDto.getEmailUser());
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

    private UUID getOrganizationUUID(String orgName) throws FailedToSaveOrgException {
        Organization organization = organizationService.getOrganizationByName(orgName);
        if (organization == null) {
            organization = new Organization(UUID.randomUUID(), orgName);
            organizationService.saveOrganization(organization);
        }
        return organization.getId();
    }

    // private void addUserToEmployer(String domain, String user) throws FailedToSaveUserException {
    //     try {
    //         Employer employer = employerRepository.getEmployerByDomain(domain);
    //         if (employer == null) {
    //             employerRepository.saveEmployer(domain, user);
    //         }
    //     } catch (Exception e) {
    //         log.error(e.getMessage());
    //         throw new FailedToSaveUserException("Failed to save user's employer", e);
    //     }
    // }
}
