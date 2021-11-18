package com.app.restaurant.repository;

import com.app.restaurant.model.ReceiptItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptItemRepository extends JpaRepository<ReceiptItem, Integer> {
}
