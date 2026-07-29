package com.learnhub.backend.user.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {

    private Long activeResources;

    private Long completedResources;

    private BigDecimal totalInvestment;

    private List<ContinueLearningResponse> continueLearning;
}