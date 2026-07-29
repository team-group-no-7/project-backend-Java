package com.learnhub.backend.user.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LibraryResponse {

    private Long contentId;

    private String title;

    private String category;

    private String type;

    private BigDecimal price;

    private String fileUrl;
}