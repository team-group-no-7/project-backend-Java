package com.learnhub.backend.billing.repository;

import com.learnhub.backend.billing.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findByUserId(Long userId);

    long countByUserId(Long userId);

    @Query("""
            SELECT COALESCE(SUM(p.amountPaid), 0)
            FROM Purchase p
            WHERE p.user.id = :userId
            """)
    BigDecimal totalInvestment(@Param("userId") Long userId);

    @Query("""
            SELECT p
            FROM Purchase p
            JOIN FETCH p.content c
            JOIN FETCH c.category
            WHERE p.user.id = :userId
            """)
    List<Purchase> dashboardContents(@Param("userId") Long userId);

    @Query("""
            SELECT p
            FROM Purchase p
            JOIN FETCH p.content c
            JOIN FETCH c.category
            WHERE p.user.id = :userId
            """)
    List<Purchase> findLibraryByUserId(@Param("userId") Long userId);
}