package com.learnhub.backend.modules.payment.service.impl;

import com.learnhub.backend.modules.payment.dto.response.PurchaseResponse;
import com.learnhub.backend.modules.user.dto.response.LibraryResponse;
import com.learnhub.backend.modules.payment.entity.Purchase;
import com.learnhub.backend.modules.resource.entity.Content;
import com.learnhub.backend.modules.payment.repository.PurchaseRepository;
import com.learnhub.backend.modules.payment.service.PurchaseService;
import com.learnhub.backend.common.util.SecurityUtils;
import com.learnhub.backend.modules.resource.repository.ContentRepository;
import com.learnhub.backend.modules.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * PurchaseServiceImpl — Implementation class for Billing & Purchase Service with SLF4J logging.
 */
@Service
public class PurchaseServiceImpl implements PurchaseService {

    private static final Logger log = LoggerFactory.getLogger(PurchaseServiceImpl.class);

    private final PurchaseRepository purchaseRepository;
    private final ContentRepository contentRepository;
    private final UserRepository userRepository;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, ContentRepository contentRepository, UserRepository userRepository) {
        this.purchaseRepository = purchaseRepository;
        this.contentRepository = contentRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseResponse> getPurchaseHistory(Long userId) {
        log.info("Fetching purchase history ledger for user ID: {}", userId);
        SecurityUtils.validateOwnershipByUserId(userId, userRepository);
        return purchaseRepository.findByUserIdOrderByPurchasedAtDesc(userId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LibraryResponse> getMyLibrary(Long userId) {
        log.info("Fetching unlocked content library for user ID: {}", userId);
        SecurityUtils.validateOwnershipByUserId(userId, userRepository);
        return purchaseRepository.findByUserId(userId)
                .stream()
                .filter(p -> "SUCCESS".equals(p.getPaymentStatus()))
                .map(purchase -> {
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