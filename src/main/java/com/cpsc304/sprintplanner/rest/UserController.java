package com.cpsc304.sprintplanner.rest;

import com.cpsc304.sprintplanner.dto.UserDto;
import com.cpsc304.sprintplanner.exceptions.FailedToSaveUserException;
import com.cpsc304.sprintplanner.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

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

}
