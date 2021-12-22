package com.app.restaurant.service.implementation;

import com.app.restaurant.model.DrinkCard;
import com.app.restaurant.model.DrinkCardItem;
import com.app.restaurant.model.Expense;
import com.app.restaurant.model.Price;
import com.app.restaurant.repository.ExpenseRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
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

}
