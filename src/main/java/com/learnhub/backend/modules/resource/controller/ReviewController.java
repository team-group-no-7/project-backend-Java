package com.learnhub.backend.modules.resource.controller;

import com.learnhub.backend.common.dto.ApiResponse;
import com.learnhub.backend.modules.resource.dto.response.ReviewResponse;
import com.learnhub.backend.modules.resource.entity.Review;
import com.learnhub.backend.modules.resource.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ReviewController — REST Controller for Content Reviews using ReviewResponse DTOs.
 * Refactored to delegate business rules strictly to ReviewService.
 */
@RestController
@RequestMapping("/api/contents")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{contentId}/reviews")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getReviews(@PathVariable Long contentId) {
        List<ReviewResponse> dtoList = reviewService.getReviewsForContent(contentId);
        return ResponseEntity.ok(ApiResponse.success("Reviews fetched successfully", dtoList));
    }

    @PostMapping("/{contentId}/reviews")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<ReviewResponse>> addReview(
            @PathVariable Long contentId,
            @RequestBody Review review) {

        ReviewResponse response = reviewService.addReview(contentId, review);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Review submitted successfully", response));
    }
}
