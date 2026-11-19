package com.matteovelez.business.inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    List<StockMovement> findByProduct_IdOrderByCreatedAtDesc(Long productId);
}
