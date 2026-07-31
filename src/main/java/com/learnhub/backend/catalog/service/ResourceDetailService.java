package com.learnhub.backend.catalog.service;

import com.learnhub.backend.catalog.dto.ResourceDetailResponse;

/**
 * ResourceDetailService — Service interface for single resource detail lookups.
 */
public interface ResourceDetailService {

    ResourceDetailResponse getResourceDetails(Long id);
}
