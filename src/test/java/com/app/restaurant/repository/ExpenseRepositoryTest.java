package com.app.restaurant.repository;

import com.app.restaurant.model.Expense;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class ExpenseRepositoryTest {

    @Autowired
    ExpenseRepository expenseRepository;

    @Test
    public void testFindAllByDates(){

        List<Expense> expenseList = expenseRepository.findByDates(1, 1637193115);
        assertEquals(1,expenseList.size());
    }

    @Test
    public void testFindByDate(){

        List<Expense> expenseList = expenseRepository.findByDate(1637193115);
        assertEquals(1,expenseList.size());
    }


}
