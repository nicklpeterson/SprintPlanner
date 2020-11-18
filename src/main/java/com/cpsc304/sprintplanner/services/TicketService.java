package com.cpsc304.sprintplanner.services;

import com.cpsc304.sprintplanner.dto.TicketDto;
import com.cpsc304.sprintplanner.exceptions.FailedToDeleteDataException;
import com.cpsc304.sprintplanner.exceptions.FailedToFetchTickets;
import com.cpsc304.sprintplanner.exceptions.FailedToSaveTicketException;
import com.cpsc304.sprintplanner.exceptions.FailedToUpdateTicket;
import com.cpsc304.sprintplanner.persistence.entities.Ticket;

import java.util.List;
import java.util.UUID;

public interface TicketService {
    List<TicketDto> getAllTickets();
    void storeTicket(TicketDto ticketDto) throws FailedToSaveTicketException;
    List<Ticket> getAllTicketsByUserName(String username) throws FailedToFetchTickets;
    List<Ticket> getAllTicketsByStatus(UUID userId, Integer sprintId, String status) throws FailedToFetchTickets;
    void updateTicketStatus(UUID ticketId, String newStatus) throws FailedToUpdateTicket;
    Integer getUsersPoints(UUID userId, Integer sprintNumber) throws Exception;
    void deleteTicket(UUID ticketId) throws FailedToDeleteDataException;
}
