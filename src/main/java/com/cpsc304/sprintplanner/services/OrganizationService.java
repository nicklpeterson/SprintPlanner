package com.cpsc304.sprintplanner.services;

import com.cpsc304.sprintplanner.exceptions.OrganizationServiceException;
import com.cpsc304.sprintplanner.persistence.entities.Organization;

import java.util.List;

public interface OrganizationService {
    Organization getOrganizationByName(String name) throws OrganizationServiceException;
    List<Organization> getAllOrganizations() throws OrganizationServiceException;
    void saveOrganization(Organization organization) throws OrganizationServiceException;
}
