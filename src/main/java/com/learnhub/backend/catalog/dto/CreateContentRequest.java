package com.learnhub.backend.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * CreateContentRequest — DTO carrying content publishing form inputs (Articles or PDFs).
 *
 * Implemented in pure Java with explicit getters, setters, and constructors (no Lombok).
 */
public class CreateContentRequest {

    @NotBlank(message = "Title cannot be empty")
    private String title;

    private String description;
    private String previewText;
    private String contentBody; // Rich Text HTML/Markdown from WYSIWYG editor
    private String fileUrl;     // Download/View URL for PDF resources

    private Double price = 0.00;
    private String type = "ARTICLE"; // ARTICLE, PDF, COURSE
    private String level = "Beginner"; // Beginner, Intermediate, Advanced
    private String tags;
    private String status = "PUBLISHED"; // DRAFT or PUBLISHED

    private Long categoryId;
    private String categoryName; // e.g. "Development", "Data Science", "AI Engineering"

    @NotNull(message = "Creator ID is required")
    private Long creatorId;

    // Default Constructor (Required by Jackson for JSON deserialization)
    public CreateContentRequest() {
    }

    // Parameterized Constructor
    public CreateContentRequest(String title, String description, String previewText, String contentBody, String fileUrl, Double price, String type, String level, String tags, String status, Long categoryId, String categoryName, Long creatorId) {
        this.title = title;
        this.description = description;
        this.previewText = previewText;
        this.contentBody = contentBody;
        this.fileUrl = fileUrl;
        this.price = price;
        this.type = type;
        this.level = level;
        this.tags = tags;
        this.status = status;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.creatorId = creatorId;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPreviewText() {
        return previewText;
    }

    public void setPreviewText(String previewText) {
        this.previewText = previewText;
    }

    public String getContentBody() {
        return contentBody;
    }

    public void setContentBody(String contentBody) {
        this.contentBody = contentBody;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    @Override
    public String toString() {
        return "CreateContentRequest{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", creatorId=" + creatorId +
                '}';
    }
}
