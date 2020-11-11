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
}
