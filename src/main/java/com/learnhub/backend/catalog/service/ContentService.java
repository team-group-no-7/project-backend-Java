package com.learnhub.backend.catalog.service;

import com.learnhub.backend.catalog.dto.response.ContentResponse;

import java.util.List;

public interface ContentService {

    List<ContentResponse> getAllContents();

    List<ContentResponse> getFeaturedContents();

    List<ContentResponse> getTrendingContents();

    ContentResponse getContentById(Long id);
}