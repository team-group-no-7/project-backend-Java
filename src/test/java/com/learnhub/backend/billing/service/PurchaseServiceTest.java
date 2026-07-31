package com.learnhub.backend.billing.service;

import com.learnhub.backend.billing.dto.response.PurchaseResponse;
import com.learnhub.backend.billing.entity.Purchase;
import com.learnhub.backend.billing.repository.PurchaseRepository;
import com.learnhub.backend.billing.service.impl.PurchaseServiceImpl;
import com.learnhub.backend.catalog.entity.Category;
import com.learnhub.backend.catalog.entity.Content;
import com.learnhub.backend.catalog.enums.ContentType;
import com.learnhub.backend.user.dto.response.LibraryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @InjectMocks
    private PurchaseServiceImpl purchaseService;

    private Purchase samplePurchase;

    @BeforeEach
    void setUp() {
        Category category = new Category();
        category.setName("Web Development");

        Content content = new Content();
        content.setId(201L);
        content.setTitle("React Guide");
        content.setType("PDF");
        content.setPrice(new BigDecimal("299.00"));
        content.setCategory(category);
        content.setFileUrl("https://example.com/react.pdf");

        samplePurchase = new Purchase();
        samplePurchase.setId(50L);
        samplePurchase.setContent(content);
        samplePurchase.setAmountPaid(new BigDecimal("299.00"));
        samplePurchase.setPaymentStatus("SUCCESS");
        samplePurchase.setPurchasedAt(LocalDateTime.now());
    }

    @Test
    @DisplayName("Should return purchase history list ordered by date")
    void getPurchaseHistory_Success() {
        when(purchaseRepository.findByUserIdOrderByPurchasedAtDesc(1L)).thenReturn(List.of(samplePurchase));

        List<PurchaseResponse> history = purchaseService.getPurchaseHistory(1L);

        assertNotNull(history);
        assertEquals(1, history.size());
        assertEquals("React Guide", history.get(0).getTitle());
        assertEquals("SUCCESS", history.get(0).getPaymentStatus());
        verify(purchaseRepository, times(1)).findByUserIdOrderByPurchasedAtDesc(1L);
    }

    @Test
    @DisplayName("Should return my library items for learner")
    void getMyLibrary_Success() {
        when(purchaseRepository.findLibraryByUserId(1L)).thenReturn(List.of(samplePurchase));

        List<LibraryResponse> library = purchaseService.getMyLibrary(1L);

        assertNotNull(library);
        assertEquals(1, library.size());
        assertEquals(201L, library.get(0).getContentId());
        assertEquals("React Guide", library.get(0).getTitle());
        assertEquals("Web Development", library.get(0).getCategory());
        verify(purchaseRepository, times(1)).findLibraryByUserId(1L);
    }
}
