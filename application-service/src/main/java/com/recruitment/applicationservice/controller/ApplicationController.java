package com.recruitment.applicationservice.controller;

import com.recruitment.applicationservice.model.Application;
import com.recruitment.applicationservice.service.ApplicationService;
import com.recruitment.applicationservice.security.JwtUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService service;
    private final JwtUtil jwtUtil;

    public ApplicationController(ApplicationService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    // POST candidature
    @PostMapping
    public Application apply(@RequestBody Application app,
                             @RequestHeader("Authorization") String token) {

        Long userId = jwtUtil.extractUserId(token);
        app.setCandidateId(userId);

        app.setStatus("PENDING");

        return service.apply(app);
    }

    @GetMapping("/{id}")
    public Application get(@PathVariable Long id,
                           @RequestHeader("Authorization") String token) {

        Application app = service.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found"));

        Long userId = jwtUtil.extractUserId(token);
        String role = jwtUtil.extractRole(token);

        // candidat → accès uniquement à ses propres candidatures
        if ("CANDIDATE".equals(role)) {
            if (!app.getCandidateId().equals(userId)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                        "You cannot access this application");
            }
        }

        return app;
    }
    @PutMapping("/{id}/status")
    public Application updateStatus(@PathVariable Long id,
                                    @RequestParam String status,
                                    @RequestHeader("Authorization") String token) {

        Long userId = jwtUtil.extractUserId(token);
        String role = jwtUtil.extractRole(token);

        if (!"RECRUTEUR".equals(role)) {
            throw new RuntimeException("FORBIDDEN: only recruiter can update");
        }

        Application app = service.getById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        // 🔴 Vérifier ownership du job
        boolean isOwner = service.isRecruiterOwnerOfJob(app.getJobId(), userId);

        if (!isOwner) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "not your job");
        }

        return service.updateStatus(app, status);
    }
}