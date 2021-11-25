package com.app.restaurant.controller;

import com.app.restaurant.dto.ExpenseDTO;
import com.app.restaurant.model.Expense;
import com.app.restaurant.service.implementation.ExpenseService;
import com.app.restaurant.support.ExpenseDTOToExpense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ExpenseDTOToExpense expenseConverter;

    @Autowired
    public ExpenseController(ExpenseService expenseService, ExpenseDTOToExpense expenseConverter) {
        this.expenseService = expenseService;
        this.expenseConverter = expenseConverter;
    }

    @GetMapping("")
    public ResponseEntity<List<ExpenseDTO>> getAll(){
        List<Expense> expenses = expenseService.findAll();
        List<ExpenseDTO> expensesDTO = new ArrayList<>();
        for (Expense e: expenses
             ) {
            expensesDTO.add(new ExpenseDTO(e));
        }

        return new ResponseEntity<>(expensesDTO, HttpStatus.OK);
    }

    @GetMapping("/{start_date}/{end_date}")
    @PreAuthorize("hasAuthority('ROLE_DIRECTOR', 'ROLE_MANAGER')")
    public ResponseEntity<List<ExpenseDTO>> getByDates(@PathVariable Long start_date, @PathVariable Long end_date){
        List<Expense> expenses = expenseService.getByDates(start_date, end_date);
        List<ExpenseDTO> expensesDTO = new ArrayList<>();
        for (Expense e: expenses
        ) {
            expensesDTO.add(new ExpenseDTO(e));
        }

        return new ResponseEntity<>(expensesDTO, HttpStatus.OK);
    }

    @GetMapping("/{date}")
    @PreAuthorize("hasAuthority('ROLE_DIRECTOR', 'ROLE_MANAGER')")
    public ResponseEntity<List<ExpenseDTO>> getByDate(@PathVariable Long date){
        List<Expense> expenses = expenseService.getByDate(date);
        List<ExpenseDTO> expensesDTO = new ArrayList<>();
        for (Expense e: expenses
        ) {
            expensesDTO.add(new ExpenseDTO(e));
        }

        return new ResponseEntity<>(expensesDTO, HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_DIRECTOR', 'ROLE_MANAGER')")
    public ResponseEntity<ExpenseDTO> createExpense(@RequestBody ExpenseDTO expenseDTO){
        Expense e = expenseService.create(expenseConverter.convert(expenseDTO));

        ExpenseDTO eDTO = new ExpenseDTO(e);

        return new ResponseEntity<>(eDTO, HttpStatus.CREATED);

    }

    @PutMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_DIRECTOR', 'ROLE_MANAGER')")
    public ResponseEntity<ExpenseDTO> updateExpense( @RequestBody ExpenseDTO expenseDTO) throws Exception {
        Expense e = expenseService.update(expenseConverter.convert(expenseDTO));

        ExpenseDTO eDTO = new ExpenseDTO(e);

        return new ResponseEntity<>(eDTO, HttpStatus.OK);

    }

    @DeleteMapping(value="/{id}")
    @PreAuthorize("hasAuthority('ROLE_DIRECTOR', 'ROLE_MANAGER')")
    public ResponseEntity<?> deleteExpense(@PathVariable Integer id) throws Exception {
        expenseService.delete(id);

        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }


}
