package com.learnhub.backend.catalog.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentReaderResponse {

    private Long id;

    private String title;

    private String description;

    private String type;

    private String contentBody;

    private String fileUrl;

    private BigDecimal price;

    private String category;
}