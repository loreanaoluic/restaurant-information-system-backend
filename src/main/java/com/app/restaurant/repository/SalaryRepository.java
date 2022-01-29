package com.app.restaurant.repository;
import com.app.restaurant.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalaryRepository extends JpaRepository<Salary, Integer> {

    @Query(value = "SELECT * FROM Salaries s WHERE s.start_date >= ?1 AND s.end_date <= ?2", nativeQuery = true)
    List<Salary> findByDates(long start_date, long end_date);
}
