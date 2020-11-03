package com.cpsc304.sprintplanner.services;

import com.cpsc304.sprintplanner.exceptions.FailedToSaveOrgException;
import com.cpsc304.sprintplanner.persistence.entities.Organization;

import java.util.List;

public interface OrganizationService {
    Organization getOrganizationByName(String name) throws FailedToSaveOrgException;
    List<Organization> getAllOrganizations() throws FailedToSaveOrgException;
    void saveOrganization(Organization organization) throws FailedToSaveOrgException;
}
