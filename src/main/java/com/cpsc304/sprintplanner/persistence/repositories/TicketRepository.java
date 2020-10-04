package com.cpsc304.sprintplanner.persistence.repositories;

import com.cpsc304.sprintplanner.persistence.entities.Ticket;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, String> {
    @Query(value="SELECT * FROM tickets", nativeQuery=true)
    Iterable<Ticket> selectAllTickets();

    @Modifying
    @Query(value="INSERT INTO TICKETS VALUES (:id, :name, :creator)", nativeQuery=true)
    void insertIntoTickets(@Param("id") int id, @Param("name") String name, @Param("creator") String creator);
}
