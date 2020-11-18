package com.cpsc304.sprintplanner.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cpsc304.sprintplanner.dto.SkillDto;
import com.cpsc304.sprintplanner.dto.UserDto;
import com.cpsc304.sprintplanner.dto.UserTokenDto;
import com.cpsc304.sprintplanner.persistence.entities.User;
import com.cpsc304.sprintplanner.services.TeamService;
import com.cpsc304.sprintplanner.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.cpsc304.sprintplanner.security.SecurityConstants.SECRET;
import static com.cpsc304.sprintplanner.security.SecurityConstants.TOKEN_PREFIX;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final TeamService teamService;


    @PostMapping(path = "/signup", consumes = "application/json")
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody UserDto user) {
        log.info("Saving User: {}", user.toString());
        final Map<String, Object> response = new HashMap<>();
        try {
            userService.saveUser(user);
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/skill", consumes = "application/json")
    public ResponseEntity<Map<String, Object>> addSkill(@RequestBody SkillDto skill, @RequestHeader("Authorization") String jwt)  {
        log.info("Adding Skill: {}", skill.toString());
        final Map<String, Object> response = new HashMap<>();
        try {
            userService.addSkill(getUserId(jwt), skill.getDescription());
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/skill/delete", consumes = "application/json")
    public ResponseEntity<Map<String, Object>> removeSkill(@RequestBody SkillDto skill, @RequestHeader("Authorization") String jwt)  {
        log.info("Removing Skill: {}", skill);
        final Map<String, Object> response = new HashMap<>();
        try {
            userService.removeSkill(getUserId(jwt), skill.getDescription());
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/skills")
    public ResponseEntity<Map<String, Object>> getSkills(@RequestHeader("Authorization") String jwt)  {
        log.info("Getting all Skills");
        final Map<String, Object> response = new HashMap<>();
        try {
            response.put("skills", userService.getSkills(getUserId(jwt)));
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/details")
    public ResponseEntity<Map<String, Object>> getUserDetails(@RequestHeader("Authorization") String jwt)  {
        log.info("Getting user details");
        final Map<String, Object> response = new HashMap<>();
        try {
            User user = userService.getUserDetails(getUserId(jwt));
            response.put("user", user);
            response.put("success", true);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            response.put("success", false);
        }
        return ResponseEntity.ok(response);
    }


    @GetMapping(path = "/profilepic/{username}")
    public ResponseEntity<Map<String, Object>> getProfilePic(@PathVariable String username) {
        log.info("Getting {}'s profile pic", username);
        final Map<String, Object> response = new HashMap<>();
        try {
            response.put("pic", new String(Base64.getEncoder().encode(userService.getProfilePic(username))));
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
