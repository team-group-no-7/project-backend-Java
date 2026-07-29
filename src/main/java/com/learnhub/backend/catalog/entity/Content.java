package com.learnhub.backend.catalog.entity;

import com.learnhub.backend.catalog.enums.ContentType;
import com.learnhub.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "contents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "preview_text", columnDefinition = "TEXT")
    private String previewText;

    @Column(name = "content_body", columnDefinition = "TEXT")
    private String contentBody;

    @Column(name = "file_url")
    private String fileUrl;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ContentType type;

    private String level;

    private String tags;

    private Boolean featured;

    @Column(name = "is_trending")
    private Boolean trending;

    private BigDecimal rating;

    @Column(name = "reviews_count")
    private Integer reviewsCount;

    @Column(name = "learners_count")
    private Integer learnersCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
