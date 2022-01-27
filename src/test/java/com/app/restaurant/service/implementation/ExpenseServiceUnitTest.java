package com.app.restaurant.service.implementation;

import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.DrinkCard;
import com.app.restaurant.model.DrinkCardItem;
import com.app.restaurant.model.Expense;
import com.app.restaurant.model.Price;
import com.app.restaurant.repository.ExpenseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ExpenseServiceUnitTest {

    @Autowired
    private ExpenseService expenseService;

    @MockBean
    private ExpenseRepository expenseRepository;

    @BeforeEach
    public void setUp() {
        List<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense(1,"nabavka salate", 2000,  1637193115, false));

        given(expenseRepository.findAll()).willReturn(expenses);

        Expense expense = new Expense(1,"nabavka salate", 2000,  1637193115, false);
        Expense savedExpense = new Expense(1,"nabavka salate", 2000,  1637193115, false);

        given(expenseRepository.findById(1))
                .willReturn(java.util.Optional.of(expense));

        given(expenseRepository.save(expense)).willReturn(savedExpense);

    }

    @Test
    public void UpdateExpense_ValidExpense_ReturnsExpense() throws Exception {
        Expense expense = new Expense(1,"nabavka salate", 2000,  1637193115, false);
        Expense created = expenseService.update(expense);

        assertEquals("nabavka salate", created.getText());
    }

    @Test
    public void UpdateExpense_InvalidExpense_ThrowsNotFoundException() {
        Expense expense = new Expense(60,"nabavka salate", 2000,  1637193115, false);

        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            expenseService.update(expense);
        });
        assertEquals("Expense with given id does not exist.", thrown.getMessage());
    }

    @Test
    public void DeleteExpense_ValidExpenseId_ReturnsVoid() {
        assertDoesNotThrow(() -> expenseService.delete(1));
    }

    @Test
    public void DeleteExpense_InvalidExpenseId_ThrowsNotFoundException() {
        assertThrows(NotFoundException.class, () -> expenseService.delete(100));
    }

    @Test
    public void CalculateValue_InvalidExpenseList_ThrowsNotFoundException() {
        assertThrows(NotFoundException.class, () -> expenseService.calculateValue(null));
    }

    @Test
    public void CalculateValue_ExpenseList_ReturnsCalculatedValue() {
        List<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense(1,"nabavka salate", 2000,  1637193115, false));
        expenses.add(new Expense(2,"nabavka salate", 2000,  1637193115, false));
        expenses.add(new Expense(3,"nabavka salate", 5000,  1637193115, false));
        assertEquals(9000, expenseService.calculateValue(expenses));
    }

    @Test
    public void CalculateValue_EmptyExpenseList_ReturnsZero() {
        List<Expense> expenses = new ArrayList<>();
        assertEquals(0, expenseService.calculateValue(expenses));
    }
}
