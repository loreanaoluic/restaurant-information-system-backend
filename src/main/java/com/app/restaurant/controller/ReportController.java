package com.app.restaurant.controller;

import com.app.restaurant.dto.ReceiptDTO;
import com.app.restaurant.dto.ReportDTO;
import com.app.restaurant.model.Receipt;
import com.app.restaurant.model.ReceiptItem;
import com.app.restaurant.model.Report;
import com.app.restaurant.service.implementation.ReceiptService;
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


    @Autowired
    private final ReceiptService receiptService;

    @Autowired
    public ReportController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }


    @GetMapping("/all")
    public ResponseEntity<ReportDTO> getAll(){
        List<Receipt> receipts = receiptService.findAll();
        System.out.println("SIZEE "+receipts.size());
        return getReportDTOResponseEntity(receipts);
    }

    @GetMapping("/{start_date}/{end_date}")
    public ResponseEntity<ReportDTO> getByDates(@PathVariable Long start_date, @PathVariable Long end_date){
        List<Receipt> receipts = receiptService.findByDates(start_date, end_date);
        return getReportDTOResponseEntity(receipts);
    }

    private ResponseEntity<ReportDTO> getReportDTOResponseEntity(List<Receipt> receipts) {
        double income = 0;
        double expense = 0;
        for (Receipt r: receipts
        ) {

            for (ReceiptItem rr:r.getReceiptItems()
                 ) {

                income += (rr.getQuantity()*rr.getItem().getPrice().getValue());
            }

        }

        Report rep = new Report(income, expense);
        ReportDTO repDTO = new ReportDTO(rep);

        return new ResponseEntity<>(repDTO, HttpStatus.OK);
    }

    @GetMapping("/{date}")
    public ResponseEntity<ReportDTO> getByDates(@PathVariable Long date){
        List<Receipt> receipts = receiptService.findByDate(date);
        return getReportDTOResponseEntity(receipts);
    }


}
