package com.cpsc304.sprintplanner.services;

import com.cpsc304.sprintplanner.dto.TicketDto;
import com.cpsc304.sprintplanner.persistence.entities.Ticket;

import java.util.List;
import java.util.UUID;

public interface TicketService {
    List<TicketDto> getAllTickets();
    void storeTicket(TicketDto ticketDto);
    List<Ticket> getAllTicketsByStatus(UUID userId, Integer sprintId, String status) throws Exception;
    void updateTicketStatus(UUID ticketId, String newStatus) throws Exception;
    Integer getUsersPoints(UUID userId, Integer sprintNumber) throws Exception;
}
