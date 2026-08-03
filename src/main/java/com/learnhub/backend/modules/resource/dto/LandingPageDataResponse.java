package com.learnhub.backend.modules.resource.dto;

import java.util.List;

/**
 * LandingPageDataResponse — Aggregated DTO containing all home/landing page sections.
 * Implemented in pure Java without Lombok.
 */
public class LandingPageDataResponse {

    private List<ContentSummaryResponse> featuredContents;
    private List<ContentSummaryResponse> trendingContents;
    private List<CategoryResponse> categories;
    private List<TopCreatorResponse> topCreators;
    private Long totalResourcesCount;
    private Long totalLearnersCount;
    private Long totalCreatorsCount;

    public LandingPageDataResponse() {
    }

    public LandingPageDataResponse(List<ContentSummaryResponse> featuredContents,
                                   List<ContentSummaryResponse> trendingContents,
                                   List<CategoryResponse> categories,
                                   List<TopCreatorResponse> topCreators,
                                   Long totalResourcesCount,
                                   Long totalLearnersCount,
                                   Long totalCreatorsCount) {
        this.featuredContents = featuredContents;
        this.trendingContents = trendingContents;
        this.categories = categories;
        this.topCreators = topCreators;
        this.totalResourcesCount = totalResourcesCount;
        this.totalLearnersCount = totalLearnersCount;
        this.totalCreatorsCount = totalCreatorsCount;
    }

    public List<ContentSummaryResponse> getFeaturedContents() {
        return featuredContents;
    }

    public void setFeaturedContents(List<ContentSummaryResponse> featuredContents) {
        this.featuredContents = featuredContents;
    }

    public List<ContentSummaryResponse> getTrendingContents() {
        return trendingContents;
    }

    public void setTrendingContents(List<ContentSummaryResponse> trendingContents) {
        this.trendingContents = trendingContents;
    }

    public List<CategoryResponse> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryResponse> categories) {
        this.categories = categories;
    }

    public List<TopCreatorResponse> getTopCreators() {
        return topCreators;
    }

    public void setTopCreators(List<TopCreatorResponse> topCreators) {
        this.topCreators = topCreators;
    }

    public Long getTotalResourcesCount() {
        return totalResourcesCount;
    }

    public void setTotalResourcesCount(Long totalResourcesCount) {
        this.totalResourcesCount = totalResourcesCount;
    }

    public Long getTotalLearnersCount() {
        return totalLearnersCount;
    }

    public void setTotalLearnersCount(Long totalLearnersCount) {
        this.totalLearnersCount = totalLearnersCount;
    }

    public Long getTotalCreatorsCount() {
        return totalCreatorsCount;
    }

    public void setTotalCreatorsCount(Long totalCreatorsCount) {
        this.totalCreatorsCount = totalCreatorsCount;
    }
}
