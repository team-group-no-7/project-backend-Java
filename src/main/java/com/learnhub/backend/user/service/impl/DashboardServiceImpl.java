package com.learnhub.backend.user.service.impl;

import com.learnhub.backend.billing.entity.Purchase;
import com.learnhub.backend.billing.repository.PurchaseRepository;
import com.learnhub.backend.user.dto.response.ContinueLearningResponse;
import com.learnhub.backend.user.dto.response.DashboardResponse;
import com.learnhub.backend.user.service.DashboardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DashboardServiceImpl — Implementation class for Learner Dashboard Service.
 * Implemented in pure Java with explicit constructor injection (no Lombok).
 */
@Service
public class DashboardServiceImpl implements DashboardService {

    private final PurchaseRepository purchaseRepository;

    public DashboardServiceImpl(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public DashboardResponse getDashboard(Long userId) {
        List<Purchase> purchases = purchaseRepository.findLibraryByUserId(userId);

        BigDecimal investment = purchaseRepository.totalInvestment(userId);
        if (investment == null) {
            investment = BigDecimal.ZERO;
        }

        List<ContinueLearningResponse> continueLearning = purchases.stream()
                .map(p -> {
                    Long contentId = p.getContent() != null ? p.getContent().getId() : null;
                    String title = p.getContent() != null ? p.getContent().getTitle() : "Untitled Resource";
                    String type = (p.getContent() != null && p.getContent().getType() != null) ? p.getContent().getType().name() : "ARTICLE";
                    String category = (p.getContent() != null && p.getContent().getCategory() != null) ? p.getContent().getCategory().getName() : "General";
                    String fileUrl = p.getContent() != null ? p.getContent().getFileUrl() : null;

                    return new ContinueLearningResponse(contentId, title, type, category, fileUrl);
                })
                .collect(Collectors.toList());

        return new DashboardResponse(
                (long) purchases.size(),
                0L,
                investment,
                continueLearning
        );
    }
}