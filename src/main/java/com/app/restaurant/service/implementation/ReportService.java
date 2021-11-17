package com.app.restaurant.service.implementation;

import com.app.restaurant.model.Report;
import com.app.restaurant.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<Report> findAll(){
        return reportRepository.findAll();
    }

    public List<Report> findByDates(long start_date, long end_date){
        return reportRepository.findByDates(start_date, end_date);
    }



}
