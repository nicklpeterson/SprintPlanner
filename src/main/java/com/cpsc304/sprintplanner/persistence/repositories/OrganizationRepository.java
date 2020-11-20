package com.cpsc304.sprintplanner.persistence.repositories;

import com.cpsc304.sprintplanner.persistence.entities.Organization;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

public interface OrganizationRepository extends CrudRepository<Organization, UUID> {
    // Selection
    @Query(value = "SELECT * FROM ORGANIZATION WHERE name = :name", nativeQuery=true)
    Organization findOrganizationByName(@Param("name") String name);

    // Selection
    @Query(value = "SELECT * FROM ORGANIZATION", nativeQuery=true)
    List<Organization> findAllOrganizations();

    // Insert Operation
    @Modifying
    @Transactional
    @Query(value =
            "INSERT INTO ORGANIZATION (orgId, name) " +
            "VALUES (:id, :name) ON CONFLICT DO NOTHING"
    , nativeQuery=true)
    void saveOrganization(@Param("id") UUID id, @Param("name") String name);
}
