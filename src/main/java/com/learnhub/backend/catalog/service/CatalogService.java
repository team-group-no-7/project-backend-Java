package com.learnhub.backend.catalog.service;

import com.learnhub.backend.catalog.dto.ContentRequest;
import com.learnhub.backend.catalog.dto.ContentResponse;
import java.util.List;

public interface CatalogService {

    List<ContentResponse> getCatalog(String search, String category);

    ContentResponse getContentById(Long id);

    ContentResponse uploadContent(ContentRequest request);

    List<ContentResponse> getContentsByCreator(Long creatorId);

    void deleteContent(Long id);

    List<ContentResponse> getFeaturedContents();

    List<com.learnhub.backend.catalog.entity.Category> getAllCategories();
}
