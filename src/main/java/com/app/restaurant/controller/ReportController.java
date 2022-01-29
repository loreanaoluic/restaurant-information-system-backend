package com.app.restaurant.controller;

import com.app.restaurant.dto.ReportDTO;
import com.app.restaurant.model.Expense;
import com.app.restaurant.model.Receipt;
import com.app.restaurant.model.Report;
import com.app.restaurant.model.Salary;
import com.app.restaurant.service.IExpenseService;
import com.app.restaurant.service.IReceiptService;
import com.app.restaurant.service.ISalaryService;
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

    private final IReceiptService receiptService;
    private final IExpenseService expenseService;
    private final ISalaryService salaryService;

    @Autowired
    public ReportController(IReceiptService receiptService, IExpenseService expenseService,
                            ISalaryService salaryService) {
        this.receiptService = receiptService;
        this.expenseService = expenseService;
        this.salaryService = salaryService;
    }


    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ROLE_DIRECTOR', 'ROLE_MANAGER')")
    public ResponseEntity<ReportDTO> getAll() {
        List<Receipt> receipts = receiptService.findAll();
        List<Expense> expenses = expenseService.findAll();
        List<Salary> salaries = salaryService.findAll();
        return getReportDTOResponseEntity(receipts, expenses, salaries, 0, System.currentTimeMillis());
    }

    @GetMapping("/{start_date}/{end_date}")
    @PreAuthorize("hasAnyAuthority('ROLE_DIRECTOR', 'ROLE_MANAGER')")
    public ResponseEntity<ReportDTO> getByDates(@PathVariable Long start_date, @PathVariable Long end_date){
        if(start_date>System.currentTimeMillis()||start_date>end_date)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        List<Receipt> receipts = receiptService.findByDates(start_date, end_date);
        List<Expense> expenses = expenseService.getByDates(start_date, end_date);
        List<Salary> salaries = null;

        salaries = salaryService.findByDates(start_date, end_date);



        return getReportDTOResponseEntity(receipts, expenses, salaries, start_date, end_date);
    }


    private ResponseEntity<ReportDTO> getReportDTOResponseEntity(List<Receipt> receipts, List<Expense> expenses,
                                                                 List<Salary> salaries, long start_date, long end_date) {

        double income = receiptService.calculateValue(receipts);
        double expense = expenseService.calculateValue(expenses);
        double salary = salaryService.calculateValue(salaries, start_date, end_date);

        expense += salary;
        Report rep = new Report(income, expense);
        ReportDTO repDTO = new ReportDTO(rep);

        return new ResponseEntity<>(repDTO, HttpStatus.OK);
    }
}
