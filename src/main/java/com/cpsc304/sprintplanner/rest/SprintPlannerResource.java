package com.cpsc304.sprintplanner.rest;

import com.cpsc304.sprintplanner.dto.TicketDto;
import com.cpsc304.sprintplanner.services.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = "/rest")
public class SprintPlannerResource {
    private final TicketService ticketService;

    @Autowired
    public SprintPlannerResource(TicketService ticketService) {
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

    @GetMapping(path="/tickets")
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
}
