package com.learnhub.backend.modules.resource.dto.response;

import java.math.BigDecimal;

/**
 * ContentReaderResponse DTO — Pure Java DTO without Lombok annotations.
 */
public class ContentReaderResponse {

    private Long id;
    private String title;
    private String description;
    private String type;
    private String contentBody;
    private String fileUrl;
    private BigDecimal price;
    private String category;

    // Default Constructor
    public ContentReaderResponse() {
    }

    // Parameterized Constructor
    public ContentReaderResponse(Long id, String title, String description, String type, String contentBody, String fileUrl, BigDecimal price, String category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.contentBody = contentBody;
        this.fileUrl = fileUrl;
        this.price = price;
        this.category = category;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ContentReaderResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}