package com.learnhub.backend.modules.resource.service.impl;

import com.learnhub.backend.modules.resource.dto.CreatorDashboardStatsDto;
import com.learnhub.backend.modules.resource.repository.ContentRepository;
import com.learnhub.backend.modules.resource.service.CreatorDashboardService;
import com.learnhub.backend.common.exception.ResourceNotFoundException;
import com.learnhub.backend.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * CreatorDashboardServiceImpl — Implementation class for calculating creator analytics.
 *
 * Implemented in pure Java with explicit constructor dependency injection (no Lombok).
 */
@Service
public class CreatorDashboardServiceImpl implements CreatorDashboardService {

    private final UserRepository userRepository;
    private final ContentRepository contentRepository;

    // Explicit constructor for dependency injection
    public CreatorDashboardServiceImpl(UserRepository userRepository, ContentRepository contentRepository) {
        this.userRepository = userRepository;
        this.contentRepository = contentRepository;
    }

    /**
     * Fetch aggregated dashboard statistics for a creator.
     *
     * @param creatorId the user ID of the creator
     * @return CreatorDashboardStatsDto containing totalResources, totalLearners, and totalEarnings
     * @throws ResourceNotFoundException if creator ID does not exist in database
     */
    @Override
    public CreatorDashboardStatsDto getDashboardStats(Long creatorId) {
        // Step 1: Verify creator exists
        userRepository.findById(creatorId)
                .orElseThrow(() -> new ResourceNotFoundException("Creator not found with id: " + creatorId));

        // Step 2: Query analytics from contentRepository
        long totalResources = contentRepository.countByCreatorId(creatorId);
        long totalLearners = contentRepository.sumLearnersCountByCreatorId(creatorId);
        Double totalEarnings = contentRepository.calculateTotalEarningsByCreatorId(creatorId);

        if (totalEarnings == null) {
            totalEarnings = 0.0;
        }

        // Step 3: Return DTO
        return new CreatorDashboardStatsDto(totalResources, totalLearners, totalEarnings);
    }
}
