package com.learnhub.backend.catalog.service;

import com.learnhub.backend.catalog.dto.CreatorDashboardStatsDto;

/**
 * CreatorDashboardService — Business logic interface for calculating creator analytics.
 */
public interface CreatorDashboardService {

    /**
     * Fetch aggregated dashboard statistics for a creator.
     *
     * @param creatorId the user ID of the creator
     * @return CreatorDashboardStatsDto containing totalResources, totalLearners, and totalEarnings
     */
    CreatorDashboardStatsDto getDashboardStats(Long creatorId);
}
