package com.learnhub.backend.modules.discussion.dto.response;

import java.time.LocalDateTime;

/**
 * QAReplyResponse — DTO representing a discussion thread reply.
 * Prevents raw JPA QAReply entity exposure.
 */
public class QAReplyResponse {

    private Long id;
    private Long threadId;
    private String authorName;
    private String role;
    private String reply;
    private Integer upvotes;
    private Boolean isVerifiedAnswer;
    private LocalDateTime createdAt;

    public QAReplyResponse() {
    }

    public QAReplyResponse(Long id, Long threadId, String authorName, String role, String reply, Integer upvotes, Boolean isVerifiedAnswer, LocalDateTime createdAt) {
        this.id = id;
        this.threadId = threadId;
        this.authorName = authorName;
        this.role = role;
        this.reply = reply;
        this.upvotes = upvotes;
        this.isVerifiedAnswer = isVerifiedAnswer;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getThreadId() {
        return threadId;
    }

    public void setThreadId(Long threadId) {
        this.threadId = threadId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Integer getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Integer upvotes) {
        this.upvotes = upvotes;
    }

    public Boolean getIsVerifiedAnswer() {
        return isVerifiedAnswer;
    }

    public void setIsVerifiedAnswer(Boolean verifiedAnswer) {
        isVerifiedAnswer = verifiedAnswer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
