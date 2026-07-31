package com.learnhub.backend.user.dto.response;

public class ContinueLearningResponse {

    private Long contentId;
    private String title;
    private String type;
    private String category;
    private String fileUrl;

    public ContinueLearningResponse() {}

    public ContinueLearningResponse(Long contentId, String title, String type, String category, String fileUrl) {
        this.contentId = contentId;
        this.title = title;
        this.type = type;
        this.category = category;
        this.fileUrl = fileUrl;
    }

    public Long getContentId() { return contentId; }
    public void setContentId(Long contentId) { this.contentId = contentId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
}