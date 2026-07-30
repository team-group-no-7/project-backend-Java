package com.learnhub.backend.user.service.impl;

import com.learnhub.backend.billing.entity.Purchase;
import com.learnhub.backend.billing.repository.PurchaseRepository;
import com.learnhub.backend.user.dto.response.ContinueLearningResponse;
import com.learnhub.backend.user.dto.response.DashboardResponse;
import com.learnhub.backend.user.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final PurchaseRepository purchaseRepository;

    public DashboardServiceImpl(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public DashboardResponse getDashboard(Long userId) {

        List<Purchase> purchases =
                purchaseRepository.findLibraryByUserId(userId);

        BigDecimal investment =
                purchaseRepository.totalInvestment(userId);
        if (investment == null) {
            investment = BigDecimal.ZERO;
        }

        List<ContinueLearningResponse> continueLearning =
                purchases.stream()
                        .map(p -> new ContinueLearningResponse(
                                p.getContent().getId(),
                                p.getContent().getTitle(),
                                p.getContent().getType().name(),
                                p.getContent().getCategory().getName(),
                                p.getContent().getFileUrl()
                        ))
                        .toList();

        return new DashboardResponse(
                (long) purchases.size(),
                0L,
                investment,
                continueLearning
        );
    }
}