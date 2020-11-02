package com.cpsc304.sprintplanner.persistence.repositories;

import com.cpsc304.sprintplanner.persistence.entities.Employer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EmployerRepository extends CrudRepository<Employer, String> {
    @Query(value =
            "INSERT INTO EMPLOYER (emailDomain, employerName) " +
            "VALUES (:#(employer.emailDomain), :#(employer.employerName))" +
            "ON CONFLICT DO NOTHING"
            , nativeQuery=true)
    void saveEmployer(@Param("employer") Employer employer);
}
