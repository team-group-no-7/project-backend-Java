package com.learnhub.backend.catalog.service.impl;

import com.learnhub.backend.catalog.dto.response.ContentReaderResponse;
import com.learnhub.backend.catalog.dto.response.CatalogResponse;
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

    private CatalogResponse mapToCatalog(Content content){

        return CatalogResponse.builder()
                .id(content.getId())
                .title(content.getTitle())
                .description(content.getDescription())
                .price(content.getPrice())
                .type(content.getType().name())
                .category(content.getCategory().getName())
                .thumbnailUrl(content.getThumbnailUrl())
                .creatorName(content.getUser().getName())
                .build();

    }
    @Override
    public ContentReaderResponse getContent(Long contentId) {

        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new RuntimeException("Content not found"));

        return ContentReaderResponse.builder()
                .id(content.getId())
                .title(content.getTitle())
                .description(content.getDescription())
                .type(content.getType().name())
                .contentBody(content.getContentBody())
                .fileUrl(content.getFileUrl())
                .price(content.getPrice())
                .category(content.getCategory().getName())
                .build();
    }
    @Override
    public List<CatalogResponse> getAllContents() {

        return contentRepository.findAll()
                .stream()
                .map(this::mapToCatalog)
                .toList();
    }

    @Override
    public List<CatalogResponse> search(String keyword) {

        return contentRepository.findByTitleContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToCatalog)
                .toList();
    }

    @Override
    public List<CatalogResponse> getByCategory(Long categoryId) {

        return contentRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::mapToCatalog)
                .toList();
    }
}