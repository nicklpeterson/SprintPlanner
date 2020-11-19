package com.cpsc304.sprintplanner.persistence.repositories;

import com.cpsc304.sprintplanner.persistence.entities.Ticket;
import com.cpsc304.sprintplanner.persistence.entities.enums.Severity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, String> {
    @Query(value="SELECT * FROM tickets", nativeQuery=true)
    Iterable<Ticket> selectAllTickets();

    // TODO: REFACTOR TO INCLUDE THE PROJECT NAME AS WELL, WILL HAVE TO DO A JOIN
    @Query(value = "SELECT * FROM TICKETS t1 WHERE assigneeId =:userId AND NOT EXISTS (SELECT t2.assigneeId FROM TICKETS T2 WHERE NOT EXISTS (SELECT t3.assigneeId, t3.status FROM TICKETS t3 WHERE t2.assigneeId = t3.assigneeId AND t3.status =:status AND sprintnumber =:sprintId))", nativeQuery=true)
    List<Ticket> findAllTicketsWithStatus(@Param("userId") UUID userId, @Param("sprintId") Integer sprintId, @Param("status") String status);

    @Query(value="SELECT coalesce(SUM(points)) FROM tickets WHERE sprintnumber=:sprintId AND assigneeid=:userId", nativeQuery=true)
    Integer getUsersPoints(@Param("userId") UUID userId, @Param("sprintId") Integer sprintId);

    @Query(value = "SELECT * FROM TICKETS WHERE assigneeid = (SELECT userid FROM USERS WHERE username = :username)", nativeQuery=true)
    List<Ticket> findAllTicketsByUserName(@Param("username") String username);

    @Modifying
    @Transactional
    @Query(value="UPDATE tickets SET STATUS=:newStatus WHERE ticketId=:ticketId", nativeQuery = true)
    void updateTicketProgressStatus(@Param("newStatus") String newStatus, @Param("ticketId")UUID ticketId);

    @Modifying
    @Transactional
    @Query(value="DELETE FROM tickets WHERE ticketId=:ticketId", nativeQuery= true)
    void deleteTicketById(@Param("ticketId") UUID ticketId);

    @Modifying
    @Transactional
    @Query(value= "INSERT INTO tickets VALUES (DEFAULT, :title, :severity, :status, :projectId, :sprintNumber,:date, :creatorId, :assigneeId, :points)", nativeQuery = true)
    void insertTicket(@Param("title") String title,
                      @Param("severity") String severity,
                      @Param("status") String status,
                      @Param("projectId") UUID projectId,
                      @Param("sprintNumber") int sprintNumber,
                      @Param("date") Timestamp date,
                      @Param("creatorId") UUID creatorId,
                      @Param("assigneeId") UUID assigneeId,
                      @Param("points") int points);
}
