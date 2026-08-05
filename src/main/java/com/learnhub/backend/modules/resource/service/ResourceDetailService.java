package com.learnhub.backend.modules.resource.service;

import com.learnhub.backend.modules.resource.dto.ResourceDetailResponse;

/**
 * ResourceDetailService — Service interface for single resource detail lookups.
 */
public interface ResourceDetailService {

    ResourceDetailResponse getResourceDetails(Long id);
}
