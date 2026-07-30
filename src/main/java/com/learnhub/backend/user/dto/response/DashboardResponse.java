package com.learnhub.backend.user.dto.response;

import java.math.BigDecimal;
import java.util.List;

public class DashboardResponse {

    private Long activeResources;
    private Long completedResources;
    private BigDecimal totalInvestment;
    private List<ContinueLearningResponse> continueLearning;

    public DashboardResponse() {}

    public DashboardResponse(Long activeResources, Long completedResources, BigDecimal totalInvestment, List<ContinueLearningResponse> continueLearning) {
        this.activeResources = activeResources;
        this.completedResources = completedResources;
        this.totalInvestment = totalInvestment;
        this.continueLearning = continueLearning;
    }

    public Long getActiveResources() { return activeResources; }
    public void setActiveResources(Long activeResources) { this.activeResources = activeResources; }

    public Long getCompletedResources() { return completedResources; }
    public void setCompletedResources(Long completedResources) { this.completedResources = completedResources; }

    public BigDecimal getTotalInvestment() { return totalInvestment; }
    public void setTotalInvestment(BigDecimal totalInvestment) { this.totalInvestment = totalInvestment; }

    public List<ContinueLearningResponse> getContinueLearning() { return continueLearning; }
    public void setContinueLearning(List<ContinueLearningResponse> continueLearning) { this.continueLearning = continueLearning; }
}