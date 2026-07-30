package com.learnhub.backend.catalog.service;

import com.learnhub.backend.catalog.dto.CategoryResponse;
import com.learnhub.backend.catalog.dto.ContentSummaryResponse;
import com.learnhub.backend.catalog.dto.LandingPageDataResponse;
import com.learnhub.backend.catalog.dto.TopCreatorResponse;

import java.util.List;

/**
 * LandingPageService — Interface for Landing Page & Public Catalog features.
 */
public interface LandingPageService {

    LandingPageDataResponse getLandingPageData();

    List<ContentSummaryResponse> getFeaturedContents();

    List<ContentSummaryResponse> getTrendingContents();

    List<CategoryResponse> getCategories();

    List<TopCreatorResponse> getTopCreators();
}
