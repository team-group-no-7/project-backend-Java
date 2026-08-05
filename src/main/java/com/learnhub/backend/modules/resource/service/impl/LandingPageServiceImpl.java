package com.learnhub.backend.modules.resource.service.impl;

import com.learnhub.backend.modules.resource.dto.CategoryResponse;
import com.learnhub.backend.modules.resource.dto.ContentSummaryResponse;
import com.learnhub.backend.modules.resource.dto.LandingPageDataResponse;
import com.learnhub.backend.modules.resource.dto.TopCreatorResponse;
import com.learnhub.backend.modules.resource.entity.Category;
import com.learnhub.backend.modules.resource.entity.Content;
import com.learnhub.backend.modules.resource.repository.CategoryRepository;
import com.learnhub.backend.modules.resource.repository.ContentRepository;
import com.learnhub.backend.modules.resource.service.LandingPageService;
import com.learnhub.backend.modules.user.entity.User;
import com.learnhub.backend.modules.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * LandingPageServiceImpl — Implementation of LandingPageService with SLF4J logging.
 */
@Service
@Transactional(readOnly = true)
public class LandingPageServiceImpl implements LandingPageService {

    private static final Logger log = LoggerFactory.getLogger(LandingPageServiceImpl.class);

    private final ContentRepository contentRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final com.learnhub.backend.modules.resource.repository.ReviewRepository reviewRepository;

    public LandingPageServiceImpl(ContentRepository contentRepository,
                                  CategoryRepository categoryRepository,
                                  UserRepository userRepository,
                                  com.learnhub.backend.modules.resource.repository.ReviewRepository reviewRepository) {
        this.contentRepository = contentRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public LandingPageDataResponse getLandingPageData() {
        log.info("Aggregating landing page data");
        List<ContentSummaryResponse> featured = getFeaturedContents();
        List<ContentSummaryResponse> trending = getTrendingContents();
        List<CategoryResponse> categories = getCategories();
        List<TopCreatorResponse> creators = getTopCreators();

        long totalResources = contentRepository.count();
        long totalLearners = userRepository.countByRole("LEARNER");
        long totalCreators = userRepository.countByRole("CREATOR");

        return new LandingPageDataResponse(
                featured,
                trending,
                categories,
                creators,
                totalResources,
                totalLearners,
                totalCreators
        );
    }

    @Override
    public List<ContentSummaryResponse> getFeaturedContents() {
        log.info("Fetching landing page featured contents");
        List<Content> contents = contentRepository.findByFeaturedTrue();
        if (contents.size() < 3) {
            List<Content> topRated = contentRepository.findTop6ByOrderByRatingDesc();
            if (!topRated.isEmpty()) {
                contents = topRated;
            } else {
                contents = contentRepository.findAll();
            }
        }
        if (contents.size() > 6) {
            contents = contents.subList(0, 6);
        }
        return contents.stream().map(this::mapToContentSummary).collect(Collectors.toList());
    }

    @Override
    public List<ContentSummaryResponse> getTrendingContents() {
        log.info("Fetching landing page trending contents");
        List<Content> contents = contentRepository.findByIsTrendingTrue();
        if (contents.isEmpty()) {
            contents = contentRepository.findTop10ByOrderByLearnersCountDesc();
        }
        return contents.stream().map(this::mapToContentSummary).collect(Collectors.toList());
    }

    @Override
    public List<CategoryResponse> getCategories() {
        log.info("Fetching landing page categories");
        List<Category> categories = categoryRepository.findAllByOrderByNameAsc();
        return categories.stream()
                .map(c -> new CategoryResponse(c.getId(), c.getName(), c.getResourceCount()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TopCreatorResponse> getTopCreators() {
        log.info("Fetching landing page top creators");
        List<User> creators = userRepository.findByRole("CREATOR");
        List<TopCreatorResponse> creatorResponses = new ArrayList<>();

        for (User creator : creators) {
            List<Content> creatorContents = contentRepository.findByCreatorId(creator.getId());
            int publishedCount = creatorContents.size();

            BigDecimal avgRating = BigDecimal.valueOf(4.8);
            if (!creatorContents.isEmpty()) {
                double avg = creatorContents.stream()
                        .mapToDouble(c -> c.getRating() != null ? c.getRating().doubleValue() : 4.5)
                        .average()
                        .orElse(4.8);
                avgRating = BigDecimal.valueOf(avg).setScale(2, java.math.RoundingMode.HALF_UP);
            }

            creatorResponses.add(new TopCreatorResponse(
                    creator.getId(),
                    creator.getName(),
                    creator.getEmail(),
                    creator.getAvatarUrl(),
                    creator.getHeadline(),
                    creator.getLocation(),
                    publishedCount,
                    avgRating
            ));
        }

        return creatorResponses;
    }

    @Override
    public List<com.learnhub.backend.modules.resource.entity.Review> getTopReviews() {
        log.info("Fetching landing page top reviews");
        List<com.learnhub.backend.modules.resource.entity.Review> reviews = reviewRepository.findAll();
        if (reviews.size() > 6) {
            return reviews.subList(0, 6);
        }
        return reviews;
    }

    private ContentSummaryResponse mapToContentSummary(Content content) {
        ContentSummaryResponse dto = new ContentSummaryResponse();
        dto.setId(content.getId());
        dto.setTitle(content.getTitle());
        dto.setDescription(content.getDescription());
        dto.setPreviewText(content.getPreviewText());
        dto.setPrice(content.getPrice());
        dto.setType(content.getType());
        dto.setLevel(content.getLevel());
        dto.setTags(content.getTags());
        dto.setFeatured(content.getFeatured());
        dto.setTrending(content.getTrending());
        dto.setRating(content.getRating());
        dto.setReviewsCount(content.getReviewsCount());
        dto.setLearnersCount(content.getLearnersCount());
        dto.setCreatedAt(content.getCreatedAt());

        if (content.getCategory() != null) {
            dto.setCategoryName(content.getCategory().getName());
        }

        if (content.getCreatorId() != null) {
            dto.setCreatorId(content.getCreatorId());
            if (content.getCreator() != null) {
                dto.setCreatorName(content.getCreator().getName());
                dto.setCreatorAvatarUrl(content.getCreator().getAvatarUrl());
            } else {
                userRepository.findById(content.getCreatorId()).ifPresent(user -> {
                    dto.setCreatorName(user.getName());
                    dto.setCreatorAvatarUrl(user.getAvatarUrl());
                });
            }
        }

        return dto;
    }
}
