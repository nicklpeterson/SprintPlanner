package com.cpsc304.sprintplanner.services;

import com.cpsc304.sprintplanner.dto.TicketDto;

import java.util.List;

public interface TicketService {
    List<TicketDto> getAllTickets();
    void storeTicket(TicketDto ticketDto);
}
