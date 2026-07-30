package com.learnhub.backend.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public class UpdateProfileRequest {

    @NotBlank(message = "Name cannot be blank")
    private String name;
    private String headline;
    private String location;
    private String avatarUrl;

    public UpdateProfileRequest() {}

    public UpdateProfileRequest(String name, String headline, String location, String avatarUrl) {
        this.name = name;
        this.headline = headline;
        this.location = location;
        this.avatarUrl = avatarUrl;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getHeadline() { return headline; }
    public void setHeadline(String headline) { this.headline = headline; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
}