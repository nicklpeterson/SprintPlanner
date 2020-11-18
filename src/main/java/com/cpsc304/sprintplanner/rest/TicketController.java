package com.cpsc304.sprintplanner.rest;

import com.cpsc304.sprintplanner.dto.TicketDto;
import com.cpsc304.sprintplanner.services.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(path = "/ticket")
public class TicketController {
    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping(path="/hello")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> sayHello() {
        // FIXME: This is just an example endpoint that should be removed
        log.info("/say/hello");
        final Map<String, Object> response = new HashMap<>();
        response.put("hello", "Hello World!");
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path="/")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getTickets() {
        // FIXME: This is just an example endpoint
        log.info("/tickets");
        final Map<String, Object> response = new HashMap<>();
        List<TicketDto> ticketDtoList = ticketService.getAllTickets();
        response.put("tickets", ticketDtoList);
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/assigned/{username}")
    public ResponseEntity<Map<String, Object>> getAssignedTickets(@PathVariable String username)  {
        log.info("Getting {}'s assigned tickets", username);
        final Map<String, Object> response = new HashMap<>();
        try {
            response.put("tickets", ticketService.getAllTicketsByUserName(username));
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<Map<String, Object>> deleteTicket(@RequestParam UUID ticketId) {
        log.info("Deleting ticket {}", ticketId.toString());
        final Map<String, Object> response = new HashMap<>();
        try {
            ticketService.deleteTicket(ticketId);
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e);
            response.put("success", false);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/new")
    public ResponseEntity<Map<String, Object>> insertTicket(@RequestBody TicketDto ticketDto) {
        log.info("Inserting New ticket {}", ticketDto.toString());
        final Map<String, Object> response = new HashMap<>();
        try {
            ticketService.storeTicket(ticketDto);
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e);
            response.put("success", false);
        }
        return ResponseEntity.ok(response);
    }
}
