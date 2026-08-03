package com.learnhub.backend.modules.resource.service;

import com.learnhub.backend.common.dto.PlatformStatsResponse;
import com.learnhub.backend.modules.resource.dto.ContentResponse;
import com.learnhub.backend.modules.resource.dto.response.CatalogResponse;
import com.learnhub.backend.modules.resource.dto.response.ContentReaderResponse;
import com.learnhub.backend.modules.resource.enums.ApprovalStatus;

import java.util.List;

public interface ContentService {

    ContentReaderResponse getContent(Long contentId);

    List<CatalogResponse> getAllContents();

    List<CatalogResponse> search(String keyword);

    List<CatalogResponse> getByCategory(Long categoryId);

    // Admin & Moderation Methods
    PlatformStatsResponse getPlatformStats();

    List<ContentResponse> getAllContentResponses();

    ContentResponse approveContent(Long contentId);

    ContentResponse flagContent(Long contentId);

    ContentResponse updateApprovalStatus(Long contentId, ApprovalStatus status);
}