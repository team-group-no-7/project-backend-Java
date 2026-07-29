package com.learnhub.backend.catalog.service.impl;

import com.learnhub.backend.catalog.dto.response.ContentResponse;
import com.learnhub.backend.catalog.entity.Content;
import com.learnhub.backend.catalog.repository.ContentRepository;
import com.learnhub.backend.catalog.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;

    @Override
    public List<ContentResponse> getAllContents() {
        return contentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ContentResponse> getFeaturedContents() {
        return contentRepository.findByFeaturedTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ContentResponse> getTrendingContents() {
        return contentRepository.findByTrendingTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ContentResponse getContentById(Long id) {

        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Content not found"));

        return mapToResponse(content);
    }

    private ContentResponse mapToResponse(Content content) {

        return ContentResponse.builder()
                .id(content.getId())
                .title(content.getTitle())
                .description(content.getDescription())
                .previewText(content.getPreviewText())
                .fileUrl(content.getFileUrl())
                .price(content.getPrice())
                .type(content.getType())
                .level(content.getLevel())
                .tags(content.getTags())
                .featured(content.getFeatured())
                .trending(content.getTrending())
                .rating(content.getRating())
                .reviewsCount(content.getReviewsCount())
                .learnersCount(content.getLearnersCount())
                .categoryName(
                        content.getCategory() != null
                                ? content.getCategory().getName()
                                : null
                )
                .creatorName(
                        content.getCreator() != null
                                ? content.getCreator().getName()
                                : null
                )
                .build();
    }
}