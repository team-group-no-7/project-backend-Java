package com.learnhub.backend.modules.resource.service;

import com.learnhub.backend.modules.resource.dto.CreatorDashboardStatsDto;

/*
 * CreatorDashboardService — Business logic interface for calculating creator analytics.
 */
public interface CreatorDashboardService {

    /*
     * Fetch aggregated dashboard statistics for a creator.
     */
    CreatorDashboardStatsDto getDashboardStats(Long creatorId);
}
