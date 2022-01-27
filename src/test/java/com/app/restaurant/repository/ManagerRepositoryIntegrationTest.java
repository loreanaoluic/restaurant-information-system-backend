package com.app.restaurant.repository;

import com.app.restaurant.model.users.Manager;
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
public class ManagerRepositoryIntegrationTest {

    @Autowired
    private ManagerRepository managerRepository;

    @Test
    public void FindByUsername_ValidUsername_ReturnsManager(){
        User user = managerRepository.findByUsername("dusan");
        assertEquals("dusan", user.getUsername());
    }

    @Test
    public void FindByUsername_InvalidUsername_ReturnsNull(){
        User user = managerRepository.findByUsername("nepostojeci");
        assertNull(user);
    }

    @Test
    public void FindAll_FindingAllNotDeletedManagers_ReturnsManagerList(){
        List<Manager> user = managerRepository.findAll();
        assertEquals(1, user.size());
    }

}
