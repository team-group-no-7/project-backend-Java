package com.learnhub.backend.catalog.dto.response;

import com.learnhub.backend.catalog.enums.ContentType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentResponse {

    private Long id;

    private String title;

    private String description;

    private String previewText;

    private String fileUrl;

    private BigDecimal price;

    private ContentType type;

    private String level;

    private String tags;

    private Boolean featured;

    private Boolean trending;

    private BigDecimal rating;

    private Integer reviewsCount;

    private Integer learnersCount;

    private String categoryName;

    private String creatorName;
}