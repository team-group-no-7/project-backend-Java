package com.learnhub.backend.modules.mentorship.controller;

import com.learnhub.backend.common.dto.ApiResponse;
import com.learnhub.backend.common.exception.ResourceNotFoundException;
import com.learnhub.backend.common.util.SecurityUtils;
import com.learnhub.backend.modules.mentorship.dto.DoubtSessionRequest;
import com.learnhub.backend.modules.mentorship.dto.DoubtSessionResponse;
import com.learnhub.backend.modules.mentorship.service.MentorshipService;
import com.learnhub.backend.modules.user.entity.User;
import com.learnhub.backend.modules.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MentorshipController — REST Controller for Learner Mentorship & Doubt Sessions.
 * Refactored with class-level @PreAuthorize and automatic JWT identity resolution.
 */
@RestController
@RequestMapping("/api/sessions")
@PreAuthorize("isAuthenticated()")
public class MentorshipController {

    private final MentorshipService service;
    private final UserRepository userRepository;

    public MentorshipController(MentorshipService service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }

    /** POST /api/sessions — Book a new mentorship doubt session */
    @PostMapping
    public ResponseEntity<ApiResponse<DoubtSessionResponse>> bookSession(@Valid @RequestBody DoubtSessionRequest request) {
        DoubtSessionResponse response = service.bookSession(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Mentorship session booked successfully", response));
    }

    /** GET /api/sessions, GET /api/sessions/my-sessions, or GET /api/sessions/{userId} */
    @GetMapping({"", "/my-sessions", "/{userId}"})
    public ResponseEntity<ApiResponse<List<DoubtSessionResponse>>> sessions(
            @PathVariable(required = false) Long userId,
            @RequestParam(value = "role", required = false) String role) {
        Long resolvedUserId = resolveUserId(userId);
        List<DoubtSessionResponse> sessions;
        if ("CREATOR".equalsIgnoreCase(role)) {
            sessions = service.getSessionsForCreator(resolvedUserId);
        } else {
            sessions = service.getSessionsForLearner(resolvedUserId);
        }
        return ResponseEntity.ok(ApiResponse.success("Mentorship sessions retrieved successfully", sessions));
    }

    /** GET /api/sessions/{sessionId}/detail — Fetch doubt session details if caller is participant or admin */
    @GetMapping("/{sessionId}/detail")
    public ResponseEntity<ApiResponse<DoubtSessionResponse>> getSessionDetail(@PathVariable Long sessionId) {
        DoubtSessionResponse response = service.getSessionDetail(sessionId);
        return ResponseEntity.ok(ApiResponse.success("Mentorship session details retrieved successfully", response));
    }

    private Long resolveUserId(Long requestedUserId) {
        String currentEmail = SecurityUtils.getCurrentUserEmail();
        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user context not found"));
        if (requestedUserId != null && !SecurityUtils.isAdmin()) {
            SecurityUtils.validateOwnershipById(currentUser.getId(), requestedUserId);
            return requestedUserId;
        }
        return (requestedUserId != null && SecurityUtils.isAdmin()) ? requestedUserId : currentUser.getId();
    }
}
