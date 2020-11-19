package com.cpsc304.sprintplanner.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cpsc304.sprintplanner.dto.TeamDto;
import com.cpsc304.sprintplanner.dto.UserTokenDto;
import com.cpsc304.sprintplanner.services.TeamService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.cpsc304.sprintplanner.security.SecurityConstants.SECRET;
import static com.cpsc304.sprintplanner.security.SecurityConstants.TOKEN_PREFIX;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    @PostMapping(path = "", consumes = "application/json")
    public ResponseEntity<Map<String, Object>> addTeam(@RequestBody TeamDto team, @RequestHeader("Authorization") String jwt)  {
        log.info("Adding Team: {}", team.toString());
        final Map<String, Object> response = new HashMap<>();
        try {
            response.put("team", teamService.addTeam(team, getUserId(jwt)));
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "")
    public ResponseEntity<Map<String, Object>> getTeams(@RequestHeader("Authorization") String jwt)  {
        log.info("Getting all teams");
        final Map<String, Object> response = new HashMap<>();
        try {
            response.put("teams", teamService.getAllTeams(getUserId(jwt)));
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/join", consumes = "application/json")
    public ResponseEntity<Map<String, Object>> joinTeam(@RequestBody TeamDto team, @RequestHeader("Authorization") String jwt)  {
        log.info("Joining Team: {}", team.toString());
        final Map<String, Object> response = new HashMap<>();
        try {
            response.put("team", teamService.joinTeam(team, getUserId(jwt)));
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/managed")
    public ResponseEntity<Map<String, Object>> getManagedTeams(@RequestHeader("Authorization") String jwt)  {
        log.info("Getting all managed teams");
        final Map<String, Object> response = new HashMap<>();
        try {
            response.put("teams", teamService.getManagedTeams(getUserId(jwt)));
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        return ResponseEntity.ok(response);
    }

    private static UUID getUserId(String jwt) throws JsonProcessingException {
        String userString = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(jwt.replace(TOKEN_PREFIX, ""))
                .getSubject();
        ObjectMapper mapper = new ObjectMapper();
        UserTokenDto user = mapper.readValue(userString, UserTokenDto.class);
        return user.getId();
    }

}
