package com.app.restaurant.repository;

import com.app.restaurant.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Integer> {


    List<Report> findAll();


    @Query("select r from Report r where r.date between  ?1 and ?2")
    List<Report> findByDates(long start_date, long end_date);
}
