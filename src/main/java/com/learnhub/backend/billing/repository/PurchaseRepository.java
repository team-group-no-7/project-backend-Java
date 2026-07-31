package com.learnhub.backend.billing.repository;

import com.learnhub.backend.billing.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    Optional<Purchase> findByTransactionId(String transactionId);

    List<Purchase> findByUserId(Long userId);

    Optional<Purchase> findByUserIdAndContentId(Long userId, Long contentId);

    List<Purchase> findByUserIdOrderByPurchasedAtDesc(Long userId);

    long countByUserId(Long userId);

    @Query("SELECT COALESCE(SUM(p.amountPaid), 0) FROM Purchase p WHERE p.userId = :userId")
    BigDecimal totalInvestment(@Param("userId") Long userId);

    @Query("SELECT p FROM Purchase p JOIN FETCH p.content c JOIN FETCH c.category WHERE p.userId = :userId ORDER BY p.purchasedAt DESC")
    List<Purchase> dashboardContents(@Param("userId") Long userId);

    @Query("SELECT p FROM Purchase p JOIN FETCH p.content c JOIN FETCH c.category WHERE p.userId = :userId ORDER BY p.purchasedAt DESC")
    List<Purchase> findLibraryByUserId(@Param("userId") Long userId);
}
