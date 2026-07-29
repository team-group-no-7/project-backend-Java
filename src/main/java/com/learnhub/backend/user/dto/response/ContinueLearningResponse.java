package com.learnhub.backend.user.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
public class ContinueLearningResponse {

    private Long contentId;

    private String title;

    private String type;

    private String category;

    private String fileUrl;
}