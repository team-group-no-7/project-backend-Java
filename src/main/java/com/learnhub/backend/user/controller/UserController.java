package com.learnhub.backend.user.controller;

import com.learnhub.backend.user.entity.User;
import com.learnhub.backend.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * UserController — Placeholder endpoint for User Profile Module.
 * Dedicated package area for User Profile & Settings Management.
 *
 * Implemented in pure Java with explicit constructor dependency injection (no Lombok).
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // Explicit constructor for dependency injection (no Lombok @RequiredArgsConstructor)
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/status")
    public String getStatus() {
        return "User Profile Module is Active";
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
