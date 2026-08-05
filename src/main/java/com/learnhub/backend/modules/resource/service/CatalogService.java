package com.learnhub.backend.modules.resource.service;

import com.learnhub.backend.modules.resource.dto.ContentRequest;
import com.learnhub.backend.modules.resource.dto.ContentResponse;
import com.learnhub.backend.modules.resource.entity.Category;

import java.util.List;

public interface CatalogService {

    List<ContentResponse> getCatalog(String search, String category);

    ContentResponse getContentById(Long id);

    ContentResponse uploadContent(ContentRequest request);

    List<ContentResponse> getContentsByCreator(Long creatorId);

    void deleteContent(Long id);

    List<ContentResponse> getFeaturedContents();

    List<Category> getAllCategories();
}
