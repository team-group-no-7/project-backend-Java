package com.learnhub.backend.modules.resource.service.impl;

import com.learnhub.backend.modules.resource.dto.CreatorDashboardStatsDto;
import com.learnhub.backend.modules.resource.repository.ContentRepository;
import com.learnhub.backend.modules.resource.service.CreatorDashboardService;
import com.learnhub.backend.common.exception.ResourceNotFoundException;
import com.learnhub.backend.modules.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * CreatorDashboardServiceImpl — Implementation class for calculating creator analytics with SLF4J logging.
 */
@Service
public class CreatorDashboardServiceImpl implements CreatorDashboardService {

    private static final Logger log = LoggerFactory.getLogger(CreatorDashboardServiceImpl.class);

    private final UserRepository userRepository;
    private final ContentRepository contentRepository;

    public CreatorDashboardServiceImpl(UserRepository userRepository, ContentRepository contentRepository) {
        this.userRepository = userRepository;
        this.contentRepository = contentRepository;
    }

    @Override
    public CreatorDashboardStatsDto getDashboardStats(Long creatorId) {
        log.info("Calculating creator dashboard analytics for creator ID: {}", creatorId);
        userRepository.findById(creatorId)
                .orElseThrow(() -> new ResourceNotFoundException("Creator not found with id: " + creatorId));

        long totalResources = contentRepository.countByCreatorId(creatorId);
        long totalLearners = contentRepository.sumLearnersCountByCreatorId(creatorId);
        Double totalEarnings = contentRepository.calculateTotalEarningsByCreatorId(creatorId);

        if (totalEarnings == null) {
            totalEarnings = 0.0;
        }

        log.info("Creator ID: {} stats — Resources: {}, Learners: {}, Earnings: ₹{}", creatorId, totalResources, totalLearners, totalEarnings);
        return new CreatorDashboardStatsDto(totalResources, totalLearners, totalEarnings);
    }
}
