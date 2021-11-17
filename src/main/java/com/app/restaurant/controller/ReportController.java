package com.app.restaurant.controller;

import com.app.restaurant.dto.ReportDTO;
import com.app.restaurant.model.Report;
import com.app.restaurant.service.implementation.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/reports")
public class ReportController {



    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<ReportDTO>> getAll(){
        List<Report> reports = reportService.findAll();
        List<ReportDTO> reportsDTO = new ArrayList<>();
        for (Report r: reports
             ) {
            reportsDTO.add(new ReportDTO(r));
        }
        return new ResponseEntity<>(reportsDTO, HttpStatus.OK);
    }

    @GetMapping("/{start_date}/{end_date}")
    public ResponseEntity<List<ReportDTO>> getByDate(@PathVariable Long start_date, @PathVariable Long end_date){
        List<Report> reports = reportService.findByDates(start_date, end_date);
        List<ReportDTO> reportsDTO = new ArrayList<>();
        for (Report r: reports
        ) {
            reportsDTO.add(new ReportDTO(r));
        }
        return new ResponseEntity<>(reportsDTO, HttpStatus.OK);
    }


}
