package com.learnhub.backend.user.controller;

import com.learnhub.backend.user.entity.User;
import com.learnhub.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * UserController — Placeholder endpoint for User Profile Module.
 * Dedicated package area for User Profile & Settings Management.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/status")
    public String getStatus() {
        return "User Profile Module is Active";
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}

