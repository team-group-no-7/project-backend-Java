package com.learnhub.backend.mentorship.controller;

import com.learnhub.backend.mentorship.dto.response.SessionResponse;
import com.learnhub.backend.mentorship.service.MentorshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class MentorshipController {

    private final MentorshipService service;

    @GetMapping("/{learnerId}")
    public List<SessionResponse> sessions(
            @PathVariable Long learnerId){

        return service.getLearnerSessions(learnerId);

    }

}