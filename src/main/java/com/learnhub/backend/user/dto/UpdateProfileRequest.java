package com.learnhub.backend.user.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * UpdateProfileRequest — DTO carrying user profile update form data.
 *
 * Implemented in pure Java with explicit getters, setters, and constructors (no Lombok).
 */
public class UpdateProfileRequest {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    private String headline;
    private String location;
    private String avatarUrl;

    // Default Constructor (Required by Jackson for JSON deserialization)
    public UpdateProfileRequest() {
    }

    // Parameterized Constructor
    public UpdateProfileRequest(String name, String headline, String location, String avatarUrl) {
        this.name = name;
        this.headline = headline;
        this.location = location;
        this.avatarUrl = avatarUrl;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "UpdateProfileRequest{" +
                "name='" + name + '\'' +
                ", headline='" + headline + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
