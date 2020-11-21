package com.cpsc304.sprintplanner.rest;

import com.cpsc304.sprintplanner.exceptions.FailedToFetchProjectsException;
import com.cpsc304.sprintplanner.persistence.entities.Sprint;
import com.cpsc304.sprintplanner.persistence.entities.Team;
import com.cpsc304.sprintplanner.persistence.entities.Ticket;
import com.cpsc304.sprintplanner.persistence.entities.User;
import com.cpsc304.sprintplanner.services.*;
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
    private final TeamMemberService teamMemberService;
    private final SprintService sprintService;
    private final TeamService teamService;
    private final ProjectService projectService;

    @GetMapping(path = "/getStatus/{userId}/{projectId}/{sprintId}/{status}", produces = "application/json")
    public ResponseEntity<Map<String, Object>> getAllOrganizations(@PathVariable UUID userId, @PathVariable String status, @PathVariable UUID projectId, @PathVariable String sprintId) {
        final Map<String, Object> response = new HashMap<>();
        try {
            List<Ticket> orgs = ticketService.getAllTicketsByStatus(userId, projectId, Integer.parseInt(sprintId), status);
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

    @GetMapping(path = "/getTeamMembers/{teamId}", produces = "application/json")
    public ResponseEntity<Map<String, Object>> getTeamMembers(@PathVariable UUID teamId) {
        final Map<String, Object> response = new HashMap<>();

        try {
            List<User> members = teamMemberService.getAllTeamMembers(teamId);
            response.put("tickets", members);
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/getAllSprints/{teamId}", produces = "application/json")
    public ResponseEntity<Map<String, Object>> getAllSprints(@PathVariable UUID teamId) {
        final Map<String, Object> response = new HashMap<>();

        try {
            List<Sprint> sprints = sprintService.getAllSprints(teamId);
            response.put("sprints", sprints);
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/getTotalPointsForUser/{userId}/{sprintNumber}/{projectId}", produces = "application/json")
    public ResponseEntity<Map<String, Object>> getUserPoints(@PathVariable UUID userId, @PathVariable String sprintNumber, @PathVariable UUID projectId) {
        final Map<String, Object> response = new HashMap<>();

        try {
            Integer sum = ticketService.getUsersPoints(userId, Integer.parseInt(sprintNumber), projectId);
            response.put("sum", sum);
            response.put("success", true);
        } catch (NullPointerException e) {
            Integer sum = 0;
            response.put("sum", sum);
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        return ResponseEntity.ok(response);
    }


    @GetMapping(path = "/getNumOfUsersWithTickets/{sprintNumber}/{projectId}", produces = "application/json")
    public ResponseEntity<Map<String, Object>> getNumOfUsersWithTickets(@PathVariable String sprintNumber, @PathVariable UUID projectId) {
        final Map<String, Object> response = new HashMap<>();
        try {
            Integer sum = sprintService.getNumOfUsersWithTickets(Integer.parseInt(sprintNumber), projectId);
            response.put("sum", sum);
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/getMaxPoints/{sprintNumber}/{projectId}", produces = "application/json")
    public ResponseEntity<Map<String, Object>> getMaxPoints(@PathVariable String sprintNumber, @PathVariable UUID projectId) {
        final Map<String, Object> response = new HashMap<>();

        try {
            Integer sum = sprintService.getMaxPoints(Integer.parseInt(sprintNumber), projectId);
            response.put("sum", sum);
            response.put("success", true);
        } catch (NullPointerException e) {
            Integer sum = 0;
            response.put("sum", sum);
            response.put("success", true);
        }catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/getAllTeams/{userId}", produces = "application/json")
    public ResponseEntity<Map<String, Object>> getAllTeams(@PathVariable UUID userId) {
        final Map<String, Object> response = new HashMap<>();

        try {
            List<Team> teams = teamService.getAllTeams(userId);
            response.put("teams", teams);
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/projects/{userId}", produces = "application/json")
    public ResponseEntity<Map<String, Object>> getProjectsByUserId(@PathVariable UUID userId) {
        final Map<String, Object> response = new HashMap<>();
        try {
            response.put("projects", projectService.getProjectsByUserId(userId));
        } catch(FailedToFetchProjectsException e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path= "/sprint/delete/{sprintNumber}/{projectId}", produces = "application/json")
    public ResponseEntity<Map<String, Object>> deleteSprint(@PathVariable Integer sprintNumber, @PathVariable UUID projectId) {

        final Map<String, Object> response = new HashMap<>();
        try {
            sprintService.deleteSprint(sprintNumber, projectId);
            response.put("success", true);
        } catch(Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        return ResponseEntity.ok(response);
    }

}
