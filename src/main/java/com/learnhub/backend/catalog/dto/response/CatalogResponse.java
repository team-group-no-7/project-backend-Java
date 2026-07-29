package com.learnhub.backend.catalog.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatalogResponse {

    private Long id;

    private String title;

    private String description;

    private String type;

    private String category;

    private BigDecimal price;

    private String thumbnailUrl;

    private String creatorName;

}