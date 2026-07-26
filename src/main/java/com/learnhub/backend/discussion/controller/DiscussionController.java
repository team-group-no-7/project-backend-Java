package com.learnhub.backend.discussion.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DiscussionController — Placeholder endpoint for Q&A Threads & Discussion forums.
 * Dedicated package area for Team Member working on QA forum.
 */
@RestController
@RequestMapping("/api/discussion")
public class DiscussionController {

    @GetMapping("/status")
    public String getStatus() {
        return "Discussion & Q&A Module is Active";
    }
}
