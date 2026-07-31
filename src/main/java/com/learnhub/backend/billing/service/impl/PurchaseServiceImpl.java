package com.learnhub.backend.billing.service.impl;

import com.learnhub.backend.billing.dto.response.PurchaseResponse;
import com.learnhub.backend.user.dto.response.LibraryResponse;
import com.learnhub.backend.billing.entity.Purchase;
import com.learnhub.backend.catalog.entity.Content;
import com.learnhub.backend.billing.repository.PurchaseRepository;
import com.learnhub.backend.billing.service.PurchaseService;
import com.learnhub.backend.catalog.repository.ContentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * PurchaseServiceImpl — Implementation class for Billing & Purchase Service.
 * Implemented in pure Java with explicit constructor injection (no Lombok).
 */
@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ContentRepository contentRepository;

    // Explicit Constructor Injection
    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, ContentRepository contentRepository) {
        this.purchaseRepository = purchaseRepository;
        this.contentRepository = contentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseResponse> getPurchaseHistory(Long userId) {
        return purchaseRepository.findByUserIdOrderByPurchasedAtDesc(userId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LibraryResponse> getMyLibrary(Long userId) {
        // Use simple findByUserId to avoid JOIN FETCH issues with null content entities
        return purchaseRepository.findByUserId(userId)
                .stream()
                .filter(p -> "SUCCESS".equals(p.getPaymentStatus()))
                .map(purchase -> {
                    // Prefer entity join, fall back to loading by contentId
                    Content content = purchase.getContent();
                    if (content == null && purchase.getContentId() != null) {
                        content = contentRepository.findById(purchase.getContentId()).orElse(null);
                    }
                    Long contentId = content != null ? content.getId() : purchase.getContentId();
                    String title = content != null ? content.getTitle() : "Untitled Resource";
                    String category = (content != null && content.getCategory() != null) ? content.getCategory().getName() : "General";
                    String type = (content != null && content.getType() != null) ? content.getType() : "ARTICLE";
                    var price = content != null ? content.getPrice() : java.math.BigDecimal.ZERO;
                    String fileUrl = content != null ? content.getFileUrl() : null;

                    return new LibraryResponse(contentId, title, category, type, price, fileUrl);
                })
                .collect(Collectors.toList());
    }

    private PurchaseResponse mapToDto(Purchase purchase) {
        Content content = purchase.getContent();
        Long contentId = content != null ? content.getId() : null;
        String title = content != null ? content.getTitle() : "Untitled Resource";
        String category = (content != null && content.getCategory() != null) ? content.getCategory().getName() : "General";

        return new PurchaseResponse(
                purchase.getId(),
                contentId,
                title,
                category,
                purchase.getAmountPaid(),
                purchase.getPaymentStatus(),
                purchase.getPurchasedAt()
        );
    }
}