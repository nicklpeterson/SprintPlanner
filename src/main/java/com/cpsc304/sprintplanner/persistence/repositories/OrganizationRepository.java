package com.cpsc304.sprintplanner.persistence.repositories;

import com.cpsc304.sprintplanner.persistence.entities.Organization;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OrganizationRepository extends CrudRepository<Organization, UUID> {
    @Query(value = "SELECT * FROM ORGANIZATION WHERE name=:name", nativeQuery=true)
    Organization findOrganizationByName(@Param("name") String name);

    @Query(value = "SELECT * FROM ORGANIZATION", nativeQuery=true)
    List<Organization> findAllOrganizations();

    @Query(value =
            "INSERT INTO EMPLOYER (orgId, name) " +
            "VALUES (:#(organization.id), :#(organization.name))" +
            "ON CONFLICT DO NOTHING"
            , nativeQuery=true)
    void saveOrganization(@Param("organization") Organization organization);
}
