package com.app.restaurant.repository;

import com.app.restaurant.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {


    @Query(value = "SELECT * FROM Receipts r WHERE r.issue_date BETWEEN ?1 AND ?2", nativeQuery = true)
    List<Receipt> findByDates(long start_date, long end_date);

    @Query(value = "select * from Receipts r where r.issue_date = ?1", nativeQuery = true)
    List<Receipt> findByDate(long date);
}
