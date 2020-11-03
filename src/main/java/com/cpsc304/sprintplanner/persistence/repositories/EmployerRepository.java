package com.cpsc304.sprintplanner.persistence.repositories;

import com.cpsc304.sprintplanner.persistence.entities.Employer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface EmployerRepository extends CrudRepository<Employer, String> {
   // @Modifying
   // @Transactional
   // @Query(value="INSERT INTO employer (emailDomain, name) VALUES (:domain, :user)", nativeQuery=true)
   // void saveEmployer(@Param("domain") String domain, @Param("user") String user);
//
   // @Query(value="SELECT * FROM employer WHERE name= :name", nativeQuery=true)
   // Employer getEmployerByDomain(@Param("name") String name);
}
