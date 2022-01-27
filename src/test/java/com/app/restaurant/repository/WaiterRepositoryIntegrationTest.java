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
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class WaiterRepositoryIntegrationTest {

    @Autowired
    private WaiterRepository waiterRepository;

    @Test
    public void FindByUsername_ValidUsername_ReturnsWaiter(){
        User user = waiterRepository.findByUsername("ana");
        assertEquals("ana", user.getUsername());
    }

    @Test
    public void FindByUsername_InvalidUsername_ReturnsNull(){
        User user = waiterRepository.findByUsername("nepostojeci");
        assertNull(user);
    }

    @Test
    public void FindAll_FindingAllNotDeletedWaiters_ReturnsWaiterList(){
        List<Waiter> users = waiterRepository.findAll();
        assertEquals(1, users.size());
    }
}
