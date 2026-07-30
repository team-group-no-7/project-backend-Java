package com.learnhub.backend.catalog.service.impl;

import com.learnhub.backend.catalog.dto.response.ContentReaderResponse;
import com.learnhub.backend.catalog.dto.response.CatalogResponse;
import com.learnhub.backend.catalog.entity.Content;
import com.learnhub.backend.catalog.repository.ContentRepository;
import com.learnhub.backend.catalog.service.ContentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ContentServiceImpl — Implementation class for Catalog Browsing Service.
 * Implemented in pure Java with explicit constructor injection (no Lombok).
 */
@Service
public class ContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;

    // Explicit Constructor for Dependency Injection
    public ContentServiceImpl(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    private CatalogResponse mapToCatalog(Content content) {
        String categoryName = content.getCategory() != null ? content.getCategory().getName() : "General";
        String creatorName = content.getCreator() != null ? content.getCreator().getName() : "LearnHub Creator";
        String typeName = content.getType() != null ? content.getType().name() : "ARTICLE";

        return new CatalogResponse(
                content.getId(),
                content.getTitle(),
                content.getDescription(),
                typeName,
                categoryName,
                content.getPrice(),
                content.getFileUrl(), // Using fileUrl as thumbnail fallback
                creatorName
        );
    }

    @Override
    public ContentReaderResponse getContent(Long contentId) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new RuntimeException("Content not found with id: " + contentId));

        String categoryName = content.getCategory() != null ? content.getCategory().getName() : "General";
        String typeName = content.getType() != null ? content.getType().name() : "ARTICLE";

        return new ContentReaderResponse(
                content.getId(),
                content.getTitle(),
                content.getDescription(),
                typeName,
                content.getContentBody(),
                content.getFileUrl(),
                content.getPrice(),
                categoryName
        );
    }

    @Override
    public List<CatalogResponse> getAllContents() {
        return contentRepository.findAll()
                .stream()
                .map(this::mapToCatalog)
                .collect(Collectors.toList());
    }

    @Override
    public List<CatalogResponse> search(String keyword) {
        return contentRepository.findByTitleContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToCatalog)
                .collect(Collectors.toList());
    }

    @Override
    public List<CatalogResponse> getByCategory(Long categoryId) {
        return contentRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::mapToCatalog)
                .collect(Collectors.toList());
    }
}