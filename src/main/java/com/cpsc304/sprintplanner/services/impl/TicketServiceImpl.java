package com.cpsc304.sprintplanner.services.impl;

import com.cpsc304.sprintplanner.dto.TicketDto;
import com.cpsc304.sprintplanner.persistence.entities.Ticket;
import com.cpsc304.sprintplanner.persistence.repositories.TicketRepository;
import com.cpsc304.sprintplanner.services.TicketService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;

    @Override
    public List<TicketDto> getAllTickets() {
        final Iterable<Ticket> ticketIterable = ticketRepository.findAll();
        final List<TicketDto> ticketDtoList = new ArrayList<>();
        for (Ticket ticket : ticketIterable) {
            ticketDtoList.add(newTicketDtoFromTicket(ticket));
        }
        return ticketDtoList;
    }

    @Override
    public void storeTicket(TicketDto ticketDto) {
        ticketRepository.save(newTicketFromTicketDto(ticketDto));
    }

    private TicketDto newTicketDtoFromTicket(Ticket ticket) {
        return TicketDto.builder()
                .id(ticket.getId())
                .ticketName(ticket.getTicketName())
                .creator(ticket.getCreator())
                .build();
    }

    private Ticket newTicketFromTicketDto(TicketDto ticketDto) {
        return Ticket.builder()
                .id(ticketDto.getId())
                .ticketName(ticketDto.getTicketName())
                .creator(ticketDto.getCreator())
                .build();
    }
}
