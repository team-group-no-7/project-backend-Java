package com.learnhub.backend.modules.resource.controller;

import com.learnhub.backend.modules.resource.dto.CategoryResponse;
import com.learnhub.backend.modules.resource.dto.ContentSummaryResponse;
import com.learnhub.backend.modules.resource.dto.LandingPageDataResponse;
import com.learnhub.backend.modules.resource.dto.TopCreatorResponse;
import com.learnhub.backend.modules.resource.service.LandingPageService;
import com.learnhub.backend.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * LandingPageController — Public REST Controller for Landing Page & Featured Content APIs.
 * Base URL: /api/public
 *
 * Implemented in pure Java with explicit constructor dependency injection (No Lombok).
 * Fully public endpoints accessible without authentication.
 */
@RestController
@RequestMapping("/api/public")
public class LandingPageController {

    private final LandingPageService landingPageService;

    // Explicit constructor dependency injection (No Lombok)
    public LandingPageController(LandingPageService landingPageService) {
        this.landingPageService = landingPageService;
    }

    /**
     * GET /api/public/landing
     * Returns complete aggregated data for the home page (featured, trending, categories, top creators, stats).
     */
    @GetMapping("/landing")
    public ResponseEntity<ApiResponse<LandingPageDataResponse>> getLandingPageData() {
        LandingPageDataResponse data = landingPageService.getLandingPageData();
        return ResponseEntity.ok(ApiResponse.success("Landing page data fetched successfully", data));
    }

    /**
     * GET /api/public/featured
     * Returns featured and top-rated content items.
     */
    @GetMapping("/featured")
    public ResponseEntity<ApiResponse<List<ContentSummaryResponse>>> getFeaturedContents() {
        List<ContentSummaryResponse> featured = landingPageService.getFeaturedContents();
        return ResponseEntity.ok(ApiResponse.success("Featured contents fetched successfully", featured));
    }

    /**
     * GET /api/public/trending
     * Returns trending content items.
     */
    @GetMapping("/trending")
    public ResponseEntity<ApiResponse<List<ContentSummaryResponse>>> getTrendingContents() {
        List<ContentSummaryResponse> trending = landingPageService.getTrendingContents();
        return ResponseEntity.ok(ApiResponse.success("Trending contents fetched successfully", trending));
    }

    /**
     * GET /api/public/categories
     * Returns all categories with resource counts.
     */
    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getCategories() {
        List<CategoryResponse> categories = landingPageService.getCategories();
        return ResponseEntity.ok(ApiResponse.success("Categories fetched successfully", categories));
    }

    /**
     * GET /api/public/top-creators
     * Returns top platform creators.
     */
    @GetMapping("/top-creators")
    public ResponseEntity<ApiResponse<List<TopCreatorResponse>>> getTopCreators() {
        List<TopCreatorResponse> creators = landingPageService.getTopCreators();
        return ResponseEntity.ok(ApiResponse.success("Top creators fetched successfully", creators));
    }

    /**
     * GET /api/public/reviews
     * Returns top student reviews from database.
     */
    @GetMapping("/reviews")
    public ResponseEntity<ApiResponse<List<com.learnhub.backend.modules.resource.entity.Review>>> getTopReviews() {
        List<com.learnhub.backend.modules.resource.entity.Review> reviews = landingPageService.getTopReviews();
        return ResponseEntity.ok(ApiResponse.success("Top reviews fetched successfully", reviews));
    }
}
