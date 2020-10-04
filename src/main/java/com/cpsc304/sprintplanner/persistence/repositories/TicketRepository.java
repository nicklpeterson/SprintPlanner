package com.cpsc304.sprintplanner.persistence.repositories;

import com.cpsc304.sprintplanner.persistence.entities.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, String> {
}
