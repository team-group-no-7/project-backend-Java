package com.learnhub.backend.mentorship.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * MentorshipController — Placeholder endpoint for live mentorship booking.
 * Dedicated package area for Team Member working on Mentorship & Jitsi configuration.
 */
@RestController
@RequestMapping("/api/mentorship")
public class MentorshipController {

    @GetMapping("/status")
    public String getStatus() {
        return "Mentorship & Live Booking Module is Active";
    }
}
