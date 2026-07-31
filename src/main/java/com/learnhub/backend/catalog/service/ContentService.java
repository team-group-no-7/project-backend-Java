package com.learnhub.backend.catalog.service;

import com.learnhub.backend.catalog.dto.response.CatalogResponse;
import com.learnhub.backend.catalog.dto.response.ContentReaderResponse;

import java.util.List;

public interface ContentService {

    ContentReaderResponse getContent(Long contentId);

    List<CatalogResponse> getAllContents();

    List<CatalogResponse> search(String keyword);

    List<CatalogResponse> getByCategory(Long categoryId);

}