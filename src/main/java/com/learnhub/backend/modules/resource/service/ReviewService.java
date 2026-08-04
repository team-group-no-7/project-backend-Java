package com.learnhub.backend.modules.resource.service;

import com.learnhub.backend.modules.resource.dto.response.ReviewResponse;
import com.learnhub.backend.modules.resource.entity.Review;

import java.util.List;

/**
 * ReviewService — Business logic interface for Content Reviews.
 */
public interface ReviewService {

    List<ReviewResponse> getReviewsForContent(Long contentId);

    ReviewResponse addReview(Long contentId, Review review);
}
