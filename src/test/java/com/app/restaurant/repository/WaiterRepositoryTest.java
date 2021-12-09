package com.app.restaurant.repository;

import com.app.restaurant.model.Expense;
import com.app.restaurant.model.users.User;
import com.app.restaurant.model.users.Waiter;
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
public class WaiterRepositoryTest {

    @Autowired
    private WaiterRepository waiterRepository;

    @Test
    public void findByUsername(){
        User user = waiterRepository.findByUsername("milica");
        assertEquals("milica",user.getUsername());
    }

    /*@Test
    public void findAll(){
        List<Waiter> users = waiterRepository.findAll();
        assertEquals(1,users.size());
    }*/
}
