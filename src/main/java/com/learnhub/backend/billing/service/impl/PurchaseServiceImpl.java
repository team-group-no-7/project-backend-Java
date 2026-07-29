package com.learnhub.backend.billing.service.impl;

import com.learnhub.backend.billing.dto.response.PurchaseResponse;
import com.learnhub.backend.billing.entity.Purchase;
import com.learnhub.backend.billing.repository.PurchaseRepository;
import com.learnhub.backend.billing.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;

    @Override
    public List<PurchaseResponse> getPurchaseHistory(Long userId) {

        return purchaseRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }
    @Override
    public List<LibraryResponse> getMyLibrary(Long userId) {

        return purchaseRepository.findLibraryByUserId(userId)
                .stream()
                .map(purchase -> {

                    var content = purchase.getContent();

                    return LibraryResponse.builder()
                            .contentId(content.getId())
                            .title(content.getTitle())
                            .category(content.getCategory().getName())
                            .type(content.getType().name())
                            .price(content.getPrice())
                            .fileUrl(content.getFileUrl())
                            .build();

                }).toList();
    }

    private PurchaseResponse mapToDto(Purchase purchase){

        return PurchaseResponse.builder()
                .purchaseId(purchase.getId())
                .contentId(purchase.getContent().getId())
                .title(purchase.getContent().getTitle())
                .category(purchase.getContent().getCategory().getName())
                .amountPaid(purchase.getAmountPaid())
                .paymentStatus(purchase.getPaymentStatus())
                .purchasedAt(purchase.getPurchasedAt())
                .build();
    }

}