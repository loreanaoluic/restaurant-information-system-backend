package com.app.restaurant.repository;

import com.app.restaurant.model.ReceiptItem;
import com.app.restaurant.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiptItemRepository extends JpaRepository<ReceiptItem, Integer> {
    @Query("select item from ReceiptItem item where item.deleted = false" )
    List<ReceiptItem> findAll();
}
