package com.learnhub.backend.mentorship.controller;

import com.learnhub.backend.mentorship.dto.DoubtSessionRequest;
import com.learnhub.backend.mentorship.dto.DoubtSessionResponse;
import com.learnhub.backend.mentorship.dto.response.SessionResponse;
import com.learnhub.backend.mentorship.service.MentorshipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MentorshipController — REST Controller for Learner Mentorship & Doubt Sessions.
 * Implemented in pure Java with explicit constructor injection (no Lombok).
 */
@RestController
@RequestMapping("/api/sessions")
public class MentorshipController {

    private final MentorshipService service;

    // Explicit Constructor Injection
    public MentorshipController(MentorshipService service) {
        this.service = service;
    }

    /** POST /api/sessions — Book a new mentorship doubt session */
    @PostMapping
    public ResponseEntity<DoubtSessionResponse> bookSession(@RequestBody DoubtSessionRequest request) {
        DoubtSessionResponse response = service.bookSession(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /** GET /api/sessions/{learnerId} — Fetch all sessions for a learner */
    @GetMapping("/{learnerId}")
    public List<SessionResponse> sessions(@PathVariable Long learnerId) {
        return service.getLearnerSessions(learnerId);
    }
}