package com.cpsc304.sprintplanner.rest;

import com.cpsc304.sprintplanner.exceptions.FailedToSaveOrgException;
import com.cpsc304.sprintplanner.persistence.entities.Organization;
import com.cpsc304.sprintplanner.services.OrganizationService;
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
@RequestMapping("/organization")
public class OrganizationController {
    OrganizationService organizationService;

    @GetMapping(path = "/", produces="application/json")
    public ResponseEntity<Map<String, Object>> getOrganization(@RequestParam String name) {
        final Map<String, Object> response = new HashMap<>();
        try {
            Organization org = organizationService.getOrganizationByName(name);
            response.put("organization", org);
            response.put("success", true);
        } catch (FailedToSaveOrgException e) {
            response.put("error", e.getMessage());
            response.put("success", false);
            log.error("Error while retrieving organization!", e);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/all", produces = "application/json")
    public ResponseEntity<Map<String, Object>> getAllOrganizations() {
        final Map<String, Object> response = new HashMap<>();
        try {
            List<Organization> orgs = organizationService.getAllOrganizations();
            response.put("organizations", orgs);
            response.put("success", true);
        } catch (FailedToSaveOrgException e) {
            response.put("error", e.getMessage());
            response.put("success", false);
            log.error("Error while retrieving all organizations!", e);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/", consumes = "application/json")
    public ResponseEntity<Map<String, Object>> saveOrganization(@RequestBody Organization organization) {
        final Map<String, Object> response = new HashMap<>();
        organization.setId(UUID.randomUUID());
        try {
            organizationService.saveOrganization(organization);
            response.put("success", true);
        } catch (FailedToSaveOrgException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return ResponseEntity.ok(response);
    }
}
