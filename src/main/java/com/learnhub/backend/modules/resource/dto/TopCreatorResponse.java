package com.learnhub.backend.modules.resource.dto;

import java.math.BigDecimal;

/**
 * TopCreatorResponse — DTO representing top content creators on the landing page.
 * Implemented in pure Java without Lombok.
 */
public class TopCreatorResponse {

    private Long id;
    private String name;
    private String email;
    private String avatarUrl;
    private String headline;
    private String location;
    private Integer publishedResourcesCount;
    private BigDecimal averageRating;

    public TopCreatorResponse() {
    }

    public TopCreatorResponse(Long id, String name, String email, String avatarUrl, String headline,
                              String location, Integer publishedResourcesCount, BigDecimal averageRating) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.headline = headline;
        this.location = location;
        this.publishedResourcesCount = publishedResourcesCount;
        this.averageRating = averageRating;
    }

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

    public Integer getPublishedResourcesCount() {
        return publishedResourcesCount;
    }

    public void setPublishedResourcesCount(Integer publishedResourcesCount) {
        this.publishedResourcesCount = publishedResourcesCount;
    }

    public BigDecimal getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(BigDecimal averageRating) {
        this.averageRating = averageRating;
    }
}
