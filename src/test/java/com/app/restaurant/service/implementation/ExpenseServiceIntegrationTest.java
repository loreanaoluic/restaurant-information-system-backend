package com.app.restaurant.service.implementation;

import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.DrinkCardItem;
import com.app.restaurant.model.Expense;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class ExpenseServiceIntegrationTest {
    @Autowired
    ExpenseService expenseService;

    @Test
    public void UpdateExpense_ValidExpense_ReturnsExpense() throws Exception {
        Expense expense = expenseService.findOne(1);
        expense.setText("plata sankera");
        expenseService.update(expense);
        assertEquals("plata sankera", expenseService.findOne(1).getText());
    }

    @Test
    public void UpdateExpense_InvalidExpense_ThrowsNotFoundException() throws Exception {
        Expense expense = expenseService.findOne(1);
        expense.setText("plata sankera");
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            expense.setId(60);
            expenseService.update(expense);
        });
        assertEquals("Expense with given id does not exist.", thrown.getMessage());
    }
}
