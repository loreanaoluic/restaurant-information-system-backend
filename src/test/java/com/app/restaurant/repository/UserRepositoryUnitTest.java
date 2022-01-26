package com.app.restaurant.repository;

import com.app.restaurant.model.Expense;
import com.app.restaurant.model.MenuItem;
import com.app.restaurant.model.users.User;
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
public class UserRepositoryUnitTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void FindAll_FindingAllNotDeletedUsers_ReturnsUsersList(){
        List<User> users = userRepository.findAll();
        assertEquals(7, users.size());
    }

    @Test
    public void FindByUsername_ValidUsername_ReturnsUser(){
        User user = userRepository.findByUsername("nemanja");
        assertEquals("nemanja",user.getUsername());
    }

    @Test
    public void FindByUsername_InvalidUsername_ReturnsNull(){
        User user = userRepository.findByUsername("nepostojeci");
        assertNull(user);
    }
}
