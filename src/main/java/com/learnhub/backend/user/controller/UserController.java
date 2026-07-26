package com.learnhub.backend.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController — Placeholder endpoint for User & Authentication Module.
 * Dedicated package area for Team Member working on Authentication.
 */
@RestController
@RequestMapping("/api/auth")
public class UserController {

    @GetMapping("/status")
    public String getStatus() {
        return "User & Auth Module is Active";
    }
}
