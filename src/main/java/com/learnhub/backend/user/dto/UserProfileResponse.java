package com.learnhub.backend.user.dto;

import java.time.LocalDateTime;

/*
 * UserProfileResponse — DTO representing a user's public profile information.
 */
public class UserProfileResponse {

    private Long id;
    private String name;
    private String email;
    private String role;
    private String avatarUrl;
    private String headline;
    private String location;
    private LocalDateTime joinedAt;

    // Default Constructor (Required by Jackson for JSON serialization)
    public UserProfileResponse() {
    }

    // Parameterized Constructor
    public UserProfileResponse(Long id, String name, String email, String role, String avatarUrl, String headline, String location, LocalDateTime joinedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.avatarUrl = avatarUrl;
        this.headline = headline;
        this.location = location;
        this.joinedAt = joinedAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    @Override
    public String toString() {
        return "UserProfileResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", headline='" + headline + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
