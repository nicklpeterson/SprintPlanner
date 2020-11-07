package com.cpsc304.sprintplanner.persistence.repositories;

import com.cpsc304.sprintplanner.persistence.entities.Ticket;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, String> {
    @Query(value="SELECT * FROM tickets", nativeQuery=true)
    Iterable<Ticket> selectAllTickets();

    // TODO: REFACTOR TO INCLUDE THE PROJECT NAME AS WELL, WILL HAVE TO DO A JOIN
    @Query(value = "SELECT * FROM TICKETS t1 WHERE NOT EXISTS (SELECT t2.assigneeId FROM TICKETS T2 WHERE assigneeId = :userId AND projectId = :sprintId AND NOT EXISTS (SELECT t3.assigneeId, t3.status FROM TICKETS t3 WHERE t2.assigneeId = t3.assigneeId AND t3.status = :status))", nativeQuery=true)
    List<Ticket> findAllTicketsWithStatus(@Param("userId") UUID userId, @Param("sprintId") UUID sprintId, @Param("status") String status);


    @Modifying
    @Transactional
    @Query(value="UPDATE tickets SET STATUS=:newStatus WHERE ticketId=:ticketId", nativeQuery = true)
    void updateTicketProgressStatus(@Param("newStatus") String newStatus, @Param("ticketId")UUID ticketId);
}
