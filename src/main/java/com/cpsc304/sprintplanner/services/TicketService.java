package com.cpsc304.sprintplanner.services;

import com.cpsc304.sprintplanner.dto.TicketDto;
import com.cpsc304.sprintplanner.exceptions.FailedToFetchTickets;
import com.cpsc304.sprintplanner.exceptions.FailedToUpdateTicket;
import com.cpsc304.sprintplanner.persistence.entities.Ticket;
import java.util.List;
import java.util.UUID;

public interface TicketService {
    List<TicketDto> getAllTickets();

    void storeTicket(TicketDto ticketDto);
    List<Ticket> getAllTicketsByStatus(UUID userId, UUID sprintId, String status) throws FailedToFetchTickets;
    void updateTicketStatus(UUID ticketId, String newStatus) throws FailedToUpdateTicket;
}
