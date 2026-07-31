package com.learnhub.backend.community.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "qa_threads")
public class QAThread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content_id", nullable = false)
    private Long contentId;

    @Column(name = "author_name", nullable = false)
    private String authorName;

    private String role; // LEARNER, CREATOR, ADMIN

    @Column(length = 2000, nullable = false)
    private String question;

    private Integer upvotes = 1;

    @Column(name = "is_resolved")
    private Boolean isResolved = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "thread_id")
    private List<QAReply> replies = new ArrayList<>();

    public QAThread() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getContentId() { return contentId; }
    public void setContentId(Long contentId) { this.contentId = contentId; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public Integer getUpvotes() { return upvotes; }
    public void setUpvotes(Integer upvotes) { this.upvotes = upvotes; }

    public Boolean getIsResolved() { return isResolved; }
    public void setIsResolved(Boolean resolved) { isResolved = resolved; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<QAReply> getReplies() { return replies; }
    public void setReplies(List<QAReply> replies) { this.replies = replies; }
}
