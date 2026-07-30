package com.learnhub.backend.billing.repository;

import com.learnhub.backend.billing.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * PurchaseRepository — Spring Data JPA repository for Purchase entity.
 */
@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    Optional<Purchase> findByTransactionId(String transactionId);

    List<Purchase> findByUserId(Long userId);

    Optional<Purchase> findByUserIdAndContentId(Long userId, Long contentId);
}
