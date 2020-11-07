package com.cpsc304.sprintplanner.rest;

import com.cpsc304.sprintplanner.persistence.entities.Ticket;
import com.cpsc304.sprintplanner.services.TicketService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/board")
public class BoardController {

    private final TicketService ticketService;

    @GetMapping(path = "/getStatus/{userId}/{sprintId}/{status}", produces = "application/json")
    public ResponseEntity<Map<String, Object>> getAllOrganizations(@PathVariable UUID userId, @PathVariable String status, @PathVariable UUID sprintId) {
        final Map<String, Object> response = new HashMap<>();

        try {
            List<Ticket> orgs = ticketService.getAllTicketsByStatus(userId, sprintId, status);
            response.put("tickets", orgs);
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/updateStatus", consumes = "application/json")
    public ResponseEntity<Map<String, Object>> updateTicketStatus(@RequestBody Ticket ticket, @RequestParam("status") String newStatus) {
        final Map<String, Object> response = new HashMap<>();

        try {
            ticketService.updateTicketStatus(ticket.getTicketId(), newStatus);
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        return ResponseEntity.ok(response);
    }

}

