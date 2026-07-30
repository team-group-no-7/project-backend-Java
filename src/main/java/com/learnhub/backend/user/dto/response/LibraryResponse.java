package com.learnhub.backend.user.dto.response;

import java.math.BigDecimal;

public class LibraryResponse {

    private Long contentId;
    private String title;
    private String category;
    private String type;
    private BigDecimal price;
    private String fileUrl;

    public LibraryResponse() {}

    public LibraryResponse(Long contentId, String title, String category, String type, BigDecimal price, String fileUrl) {
        this.contentId = contentId;
        this.title = title;
        this.category = category;
        this.type = type;
        this.price = price;
        this.fileUrl = fileUrl;
    }

    public Long getContentId() { return contentId; }
    public void setContentId(Long contentId) { this.contentId = contentId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
}