package com.learnhub.backend.user.service;

import com.learnhub.backend.modules.payment.entity.Purchase;
import com.learnhub.backend.modules.payment.repository.PurchaseRepository;
import com.learnhub.backend.modules.resource.entity.Category;
import com.learnhub.backend.modules.resource.entity.Content;
import com.learnhub.backend.modules.user.dto.response.DashboardResponse;
import com.learnhub.backend.modules.user.service.impl.DashboardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;

    @InjectMocks
    private DashboardServiceImpl dashboardService;

    private Purchase samplePurchase;

    @BeforeEach
    void setUp() {
        Category category = new Category();
        category.setName("Java");

        Content content = new Content();
        content.setId(101L);
        content.setTitle("Java Masterclass");
        content.setType("ARTICLE");
        content.setCategory(category);
        content.setFileUrl("https://example.com/java.pdf");

        samplePurchase = new Purchase();
        samplePurchase.setId(10L);
        samplePurchase.setContent(content);
        samplePurchase.setAmountPaid(new BigDecimal("499.00"));
    }

    @Test
    @DisplayName("Should return learner dashboard with active resources and investment")
    void getDashboard_Success() {
        when(purchaseRepository.findLibraryByUserId(1L)).thenReturn(List.of(samplePurchase));
        when(purchaseRepository.totalInvestment(1L)).thenReturn(new BigDecimal("499.00"));

        DashboardResponse response = dashboardService.getDashboard(1L);

        assertNotNull(response);
        assertEquals(1L, response.getActiveResources());
        assertEquals(new BigDecimal("499.00"), response.getTotalInvestment());
        assertEquals(1, response.getContinueLearning().size());
        assertEquals("Java Masterclass", response.getContinueLearning().get(0).getTitle());
    }

    @Test
    @DisplayName("Should return ZERO investment when user has no purchases")
    void getDashboard_ZeroInvestment() {
        when(purchaseRepository.findLibraryByUserId(1L)).thenReturn(List.of());
        when(purchaseRepository.totalInvestment(1L)).thenReturn(null);

        DashboardResponse response = dashboardService.getDashboard(1L);

        assertNotNull(response);
        assertEquals(0L, response.getActiveResources());
        assertEquals(BigDecimal.ZERO, response.getTotalInvestment());
        assertTrue(response.getContinueLearning().isEmpty());
    }
}
