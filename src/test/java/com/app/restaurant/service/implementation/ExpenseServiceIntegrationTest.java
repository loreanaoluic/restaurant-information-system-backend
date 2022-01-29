package com.app.restaurant.service.implementation;

import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.DrinkCardItem;
import com.app.restaurant.model.Expense;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class ExpenseServiceIntegrationTest {
    @Autowired
    ExpenseService expenseService;

    @Test
    public void findAll_successfullyFindsAll(){
        List<Expense> allExpenses = this.expenseService.findAll();

        assertEquals(3, allExpenses.size());
    }

    @Test
    public void findOne_existingId_returnsExpense(){
        Expense e = this.expenseService.findOne(2);

        assertNotNull(e);
        assertEquals("nabavka makarona",e.getText());
        assertEquals(2, e.getId());
        assertEquals(2690,e.getValue());
    }

    @Test
    public void findOne_nonExistingId_returnsNull(){
        Expense e = this.expenseService.findOne(22);

        assertNull(e);
    }

    @Test
    public void save_validExpense_returnsSaved(){
        Expense newExpense = new Expense();
        newExpense.setDeleted(false);
        newExpense.setText("Nabavka pribora za jelo");
        newExpense.setValue(3000);
        newExpense.setDate(System.currentTimeMillis());

        Expense saved = this.expenseService.save(newExpense);

        assertNotNull(saved);
        assertEquals(newExpense.getText(), saved.getText());
        assertEquals(newExpense.getValue(), saved.getValue());
    }

    @Test
    public void save_missingSomeFields_returnsSaved(){
        Expense newExpense = new Expense();
        newExpense.setValue(3000);
        newExpense.setDate(System.currentTimeMillis());

        Expense saved = this.expenseService.save(newExpense);

        assertNotNull(saved);
        assertEquals(newExpense.getValue(), saved.getValue());
    }

    @Test
    public void save_nullEntity_throwsInvalidDataAccessApiUsage(){
        InvalidDataAccessApiUsageException idaaue = assertThrows(InvalidDataAccessApiUsageException.class, () -> this.expenseService.save(null));
        assertEquals("Entity must not be null.; nested exception is java.lang.IllegalArgumentException: Entity must not be null.",
                idaaue.getMessage());
    }


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

    @Test
    public void delete_validId_setsExpenseToDeleted() throws Exception {
        expenseService.delete(1);

        assertNull(expenseService.findOne(1));
    }

    @Test
    public void Delete_InvalidExpenseId_ThrowsNotFoundException(){
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            expenseService.delete(420);
        });
        assertEquals("Expense with given id does not exist.", thrown.getMessage());
    }
}
