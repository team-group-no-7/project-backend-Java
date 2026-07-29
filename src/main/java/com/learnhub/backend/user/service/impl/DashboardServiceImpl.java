package com.learnhub.backend.user.service.impl;

import com.learnhub.backend.billing.entity.Purchase;
import com.learnhub.backend.billing.repository.PurchaseRepository;
import com.learnhub.backend.user.dto.response.ContinueLearningResponse;
import com.learnhub.backend.user.dto.response.DashboardResponse;
import com.learnhub.backend.user.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final PurchaseRepository purchaseRepository;

    @Override
    public DashboardResponse getDashboard(Long userId) {

        List<Purchase> purchases =
                purchaseRepository.findLibraryByUserId(userId);

        BigDecimal investment =
                purchaseRepository.totalInvestment(userId);

        List<ContinueLearningResponse> continueLearning =
                purchases.stream()
                        .map(p -> ContinueLearningResponse.builder()
                                .contentId(p.getContent().getId())
                                .title(p.getContent().getTitle())
                                .type(p.getContent().getType().name())
                                .category(p.getContent().getCategory().getName())
                                .fileUrl(p.getContent().getFileUrl())
                                .build())
                        .toList();

        return DashboardResponse.builder()
                .activeResources(purchases.size())
                .completedResources(0)
                .totalInvestment(investment)
                .continueLearning(continueLearning)
                .build();
    }
}