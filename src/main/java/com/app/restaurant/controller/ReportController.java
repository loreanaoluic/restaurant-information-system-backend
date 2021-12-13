package com.app.restaurant.controller;

import com.app.restaurant.dto.ReportDTO;
import com.app.restaurant.model.Expense;
import com.app.restaurant.model.Receipt;
import com.app.restaurant.model.Report;
import com.app.restaurant.service.implementation.ExpenseService;
import com.app.restaurant.service.implementation.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/reports")
public class ReportController {


    @Autowired
    private final ReceiptService receiptService;

    @Autowired
    private final ExpenseService expenseService;

    @Autowired
    public ReportController(ReceiptService receiptService, ExpenseService expenseService) {
        this.receiptService = receiptService;
        this.expenseService = expenseService;
    }


    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ROLE_DIRECTOR', 'ROLE_MANAGER')")
    public ResponseEntity<ReportDTO> getAll(){
        List<Receipt> receipts = receiptService.findAll();
        List<Expense> expenses = expenseService.findAll();
        return getReportDTOResponseEntity(receipts, expenses);
    }

    @GetMapping("/{start_date}/{end_date}")
    @PreAuthorize("hasAnyAuthority('ROLE_DIRECTOR', 'ROLE_MANAGER')")
    public ResponseEntity<ReportDTO> getByDates(@PathVariable Long start_date, @PathVariable Long end_date){
        List<Receipt> receipts = receiptService.findByDates(start_date, end_date);
        List<Expense> expenses = expenseService.getByDates(start_date, end_date);
        return getReportDTOResponseEntity(receipts, expenses);
    }

    private ResponseEntity<ReportDTO> getReportDTOResponseEntity(List<Receipt> receipts, List<Expense> expenses) {


        double income = receiptService.calculateValue(receipts);
        double expense = expenseService.calculateValue(expenses);
        Report rep = new Report(income, expense);
        ReportDTO repDTO = new ReportDTO(rep);

        return new ResponseEntity<>(repDTO, HttpStatus.OK);
    }

    @GetMapping("/{date}")
    @PreAuthorize("hasAnyAuthority('ROLE_DIRECTOR', 'ROLE_MANAGER')")
    public ResponseEntity<ReportDTO> getByDates(@PathVariable Long date){
        List<Receipt> receipts = receiptService.findByDate(date);
        List<Expense> expenses = expenseService.getByDate(date);
        return getReportDTOResponseEntity(receipts, expenses);
    }


}
