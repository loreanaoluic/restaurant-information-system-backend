package com.app.restaurant.service.implementation;

import com.app.restaurant.model.Role;
import com.app.restaurant.model.users.Bartender;
import com.app.restaurant.model.users.User;
import com.app.restaurant.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
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
public class UserServiceUnitTest {

    @Autowired
    public UserService userService;


    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {

        List<User> users = new ArrayList<>();
        User u1= new Bartender();
        u1.setRole(new Role(6,"Bartender"));
        u1.setId(100);
        u1.setName("Milica");
        u1.setLastName("Mitrovic");
        u1.setEmailAddress("milica@gmail.com");
        u1.setUsername("milica");
        u1.setPassword("123");
        u1.setDeleted(false);

        User u2= new Bartender();
        u2.setRole(new Role(6,"Bartender"));
        u2.setId(100);
        u2.setName("Milica");
        u2.setLastName("Mitrovic");
        u2.setEmailAddress("milica@gmail.com");
        u2.setUsername("milica");
        u2.setPassword("123");
        u2.setDeleted(false);

        users.add(u1);

        given(userRepository.findAll()).willReturn(users);

        given(userRepository.findById(100))
                .willReturn(java.util.Optional.of(u1));

        given(userRepository.save(u1)).willReturn(u2);
    }

    @Test
    public void findAll() {

        List<User> found = userService.findAll();
        System.out.println(found);
        assertEquals(1, found.size());
    }

    @Test
    public void findOne() {

        User found = userService.findOne(100);
        assertEquals(100, found.getId());
    }

    @Test
    public void update() {

        User found = userService.findOne(100);
        found.setUsername("nemanja");
        userService.save(found);
        User tmp = userService.findOne(100);
        assertEquals("nemanja", tmp.getUsername());
    }

    @Test
    public void create() {

        User createdUser= new Bartender();
        createdUser.setRole(new Role(6,"Bartender"));
        createdUser.setId(101);
        createdUser.setName("Milan");
        createdUser.setLastName("Milovanovic");
        createdUser.setEmailAddress("milan@gmail.com");
        createdUser.setUsername("milan");
        createdUser.setPassword("123");
        createdUser.setDeleted(false);

        List<User> users = userService.findAll();
        users.add(createdUser);

        List<User> found = userService.findAll();
        assertEquals(2, found.size());
    }

}
