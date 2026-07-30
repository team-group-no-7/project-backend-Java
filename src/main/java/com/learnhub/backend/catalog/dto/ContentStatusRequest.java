package com.learnhub.backend.catalog.dto;

import jakarta.validation.constraints.NotBlank;

/*
 * ContentStatusRequest — DTO carrying status toggle updates (DRAFT vs PUBLISHED).
 */
public class ContentStatusRequest {

    @NotBlank(message = "Status cannot be empty")
    private String status; // DRAFT or PUBLISHED

    // Default Constructor (Required by Jackson for JSON deserialization)
    public ContentStatusRequest() {
    }

    // Parameterized Constructor
    public ContentStatusRequest(String status) {
        this.status = status;
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ContentStatusRequest{" +
                "status='" + status + '\'' +
                '}';
    }
}
