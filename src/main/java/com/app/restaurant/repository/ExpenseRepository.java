package com.app.restaurant.repository;

import com.app.restaurant.model.Expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    List<Expense> findAll();
    @Query("select e from Expense e where e.date = ?1")
    List<Expense> findByDate(long date);

    @Query("select e from Expense e where e.date between ?1 and ?2")
    List<Expense> findByDates(long start_date, long end_date);







}
