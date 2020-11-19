package com.cpsc304.sprintplanner.services.impl;

import com.cpsc304.sprintplanner.dto.TicketDto;
import com.cpsc304.sprintplanner.exceptions.FailedToDeleteDataException;
import com.cpsc304.sprintplanner.exceptions.FailedToFetchTickets;
import com.cpsc304.sprintplanner.exceptions.FailedToSaveTicketException;
import com.cpsc304.sprintplanner.exceptions.FailedToUpdateTicket;
import com.cpsc304.sprintplanner.persistence.entities.Ticket;
import com.cpsc304.sprintplanner.persistence.entities.User;
import com.cpsc304.sprintplanner.persistence.repositories.TicketRepository;
import com.cpsc304.sprintplanner.persistence.repositories.UserRepository;
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
    private final UserRepository userRepository;

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
    public void storeTicket(TicketDto ticketDto) throws FailedToSaveTicketException {
        try {
            ticketRepository.insertTicket(ticketDto.getTicketTitle(),
                    ticketDto.getSeverity().toString(),
                    ticketDto.getStatus().toString(),
                    ticketDto.getProjectId(),
                    ticketDto.getSprintNumber(),
                    ticketDto.getDateIssue(),
                    ticketDto.getCreatorId(),
                    ticketDto.getAssigneeId(),
                    ticketDto.getPoints());
        } catch (Exception e) {
            throw new FailedToSaveTicketException(e.getMessage(), e);
        }
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
    public void updateTicketPoints(UUID ticketId, Integer points) throws FailedToUpdateTicket {
        try {
            ticketRepository.updateTicketPoints(points, ticketId);
        } catch (Exception e) {
            throw new FailedToUpdateTicket(e.getMessage(), e);
        }
    }

    @Override
    public void updateTicketAssignee(UUID ticketId, UUID userId) throws FailedToUpdateTicket {
        try {
            ticketRepository.updateTicketAssignee(userId, ticketId);
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
    public void deleteTicket(UUID ticketId) throws FailedToDeleteDataException {
        try {
            ticketRepository.deleteTicketById(ticketId);
        } catch (Exception e) {
            throw new FailedToDeleteDataException(e.getMessage(), e);
        }
    }
          
    public Ticket getTicketById(UUID ticketId) throws FailedToFetchTickets {
        try {
            return ticketRepository.getTicketById(ticketId);
        } catch (Exception e) {
            throw new FailedToFetchTickets(e.getMessage(), e);
        }
    }

    @Override
    public List<User> getAvailableMembers(UUID ticketId) throws Exception {
        try {
            log.info(userRepository.getAvailableMembers(ticketId).toString());
            return userRepository.getAvailableMembers(ticketId);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new Exception(e.getMessage(), e);
        }
    }
}