package com.cpsc304.sprintplanner.services.impl;

import com.cpsc304.sprintplanner.dto.TicketDto;
import com.cpsc304.sprintplanner.exceptions.FailedToFetchTickets;
import com.cpsc304.sprintplanner.exceptions.FailedToUpdateTicket;
import com.cpsc304.sprintplanner.persistence.entities.Ticket;
import com.cpsc304.sprintplanner.persistence.repositories.TicketRepository;
import com.cpsc304.sprintplanner.services.TicketService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;

    @Override
    public List<TicketDto> getAllTickets() {
        final Iterable<Ticket> ticketIterable = ticketRepository.selectAllTickets();
        final List<TicketDto> ticketDtoList = new ArrayList<>();
        for (Ticket ticket : ticketIterable) {
            ticketDtoList.add(newTicketDtoFromTicket(ticket));
        }
        return ticketDtoList;
    }

    @Override
    public void storeTicket(TicketDto ticketDto) {
        // TODO
    }

    private TicketDto newTicketDtoFromTicket(Ticket ticket) {
        //TODO: id is now a UUID, and violates the builder type
        return null;
//        return TicketDto.builder()
//                .id(ticket.getId())
//                .ticketName(ticket.getTicketName())
//                .creator(ticket.getCreator())
//                .build();
    }

    @Override
    public List<Ticket> getAllTicketsByStatus(UUID userId, Integer sprintId, String status) throws FailedToFetchTickets {
        try {
            return ticketRepository.findAllTicketsWithStatus(userId, sprintId, status);
        } catch (Exception e) {
            throw new FailedToFetchTickets(e.getMessage(), e);
        }
    }

    @Override
    public List<Ticket> getAllTicketsByUserName(String username) throws FailedToFetchTickets {
        try {
            return ticketRepository.findAllTicketsByUserName(username);
        } catch (Exception e) {
            log.info(e.getMessage());
            log.info(e.toString());
            throw new FailedToFetchTickets(e.getMessage(), e);
        }
    }

    @Override
    public void updateTicketStatus(UUID ticketId, String newStatus) throws FailedToUpdateTicket {
        try {
            ticketRepository.updateTicketProgressStatus(newStatus, ticketId);
        } catch (Exception e) {
            throw new FailedToUpdateTicket(e.getMessage(), e);
        }
    }

    @Override
    public Integer getUsersPoints(UUID userId, Integer sprintNumber) throws Exception {
        try {
            return ticketRepository.getUsersPoints(userId, sprintNumber);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    @Override
    public Ticket getTicketById(UUID ticketId) throws FailedToFetchTickets {
        try {
            return ticketRepository.getTicketById(ticketId);
        } catch (Exception e) {
            throw new FailedToFetchTickets(e.getMessage(), e);
        }
    }
}