package com.learnhub.backend.catalog.dto.response;

import java.math.BigDecimal;

/**
 * CatalogResponse DTO — Pure Java DTO without Lombok annotations.
 */
public class CatalogResponse {

    private Long id;
    private String title;
    private String description;
    private String type;
    private String category;
    private BigDecimal price;
    private String thumbnailUrl;
    private String creatorName;

    // Default Constructor
    public CatalogResponse() {
    }

    // Parameterized Constructor
    public CatalogResponse(Long id, String title, String description, String type, String category, BigDecimal price, String thumbnailUrl, String creatorName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.category = category;
        this.price = price;
        this.thumbnailUrl = thumbnailUrl;
        this.creatorName = creatorName;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    @Override
    public String toString() {
        return "CatalogResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                '}';
    }
}