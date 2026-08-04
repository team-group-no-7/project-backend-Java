package com.learnhub.backend.modules.user.service.impl;

import com.learnhub.backend.common.util.SecurityUtils;
import com.learnhub.backend.modules.payment.entity.Purchase;
import com.learnhub.backend.modules.payment.repository.PurchaseRepository;
import com.learnhub.backend.modules.user.dto.response.ContinueLearningResponse;
import com.learnhub.backend.modules.user.dto.response.DashboardResponse;
import com.learnhub.backend.modules.user.repository.UserRepository;
import com.learnhub.backend.modules.user.service.DashboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DashboardServiceImpl — Implementation class for Learner Dashboard Service with SLF4J logging.
 */
@Service
public class DashboardServiceImpl implements DashboardService {

    private static final Logger log = LoggerFactory.getLogger(DashboardServiceImpl.class);

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;

    public DashboardServiceImpl(PurchaseRepository purchaseRepository, UserRepository userRepository) {
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public DashboardResponse getDashboard(Long userId) {
        log.info("Computing learner dashboard metrics for user ID: {}", userId);
        SecurityUtils.validateOwnershipByUserId(userId, userRepository);
        List<Purchase> purchases = purchaseRepository.findLibraryByUserId(userId);

        BigDecimal investment = purchaseRepository.totalInvestment(userId);
        if (investment == null) {
            investment = BigDecimal.ZERO;
        }

        List<ContinueLearningResponse> continueLearning = purchases.stream()
                .map(p -> {
                    Long contentId = p.getContent() != null ? p.getContent().getId() : null;
                    String title = p.getContent() != null ? p.getContent().getTitle() : "Untitled Resource";
                    String type = (p.getContent() != null && p.getContent().getType() != null) ? p.getContent().getType() : "ARTICLE";
                    String category = (p.getContent() != null && p.getContent().getCategory() != null) ? p.getContent().getCategory().getName() : "General";
                    String fileUrl = p.getContent() != null ? p.getContent().getFileUrl() : null;

                    return new ContinueLearningResponse(contentId, title, type, category, fileUrl);
                })
                .collect(Collectors.toList());

        log.info("Learner ID: {} dashboard stats — Resources: {}, Investment: ₹{}", userId, purchases.size(), investment);
        return new DashboardResponse(
                (long) purchases.size(),
                0L,
                investment,
                continueLearning
        );
    }
}