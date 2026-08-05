package com.learnhub.backend.modules.resource.service;

import com.learnhub.backend.modules.resource.dto.CategoryResponse;
import com.learnhub.backend.modules.resource.dto.ContentSummaryResponse;
import com.learnhub.backend.modules.resource.dto.LandingPageDataResponse;
import com.learnhub.backend.modules.resource.dto.TopCreatorResponse;
import com.learnhub.backend.modules.resource.entity.Review;

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

    List<Review> getTopReviews();
}
