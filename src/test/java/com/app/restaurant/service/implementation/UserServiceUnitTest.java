package com.app.restaurant.service.implementation;

import com.app.restaurant.dto.UserDTO;
import com.app.restaurant.exception.DuplicateEntityException;
import com.app.restaurant.exception.EmptyParameterException;
import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.Role;
import com.app.restaurant.model.Salary;
import com.app.restaurant.model.users.Bartender;
import com.app.restaurant.model.users.User;
import com.app.restaurant.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
    @MockBean
    private SalaryService salaryService;

    @BeforeEach
    public void setUp() {

        List<User> users = new ArrayList<>();
        User u1 = new Bartender();
        u1.setRole(new Role(6,"Bartender"));
        u1.setId(100);
        u1.setName("Milica");
        u1.setLastName("Mitrovic");
        u1.setEmailAddress("milica@gmail.com");
        u1.setUsername("milica");
        u1.setPassword("123");
        u1.setDeleted(false);
        Salary salary = new Salary();
        salary.setId(1);
        salary.setUser(u1);
        salary.setValue(25000.0);
        u1.setSalary(salary);

        users.add(u1);

        given(userRepository.findAll()).willReturn(users);

        given(userRepository.findById(100))
                .willReturn(java.util.Optional.of(u1));

        given(userRepository.findByUsername("milica")).willReturn(u1);

        given(userRepository.save(u1)).willReturn(u1);

        given(salaryService.save(salary)).willReturn(salary);

    }

    @Test
    public void LoadByUsername_ValidUsername_ReturnsUser() {
        UserDetails userDetails = userService.loadUserByUsername("milica");
        assertEquals("milica", userDetails.getUsername());
    }

    @Test
    public void LoadByUsername_InvalidUsername_ReturnsUser() {
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            userService.loadUserByUsername("milicaa");
        });
        assertEquals("No user found for milicaa.", thrown.getMessage());
    }

    @Test
    public void FindAll_ReturnsUserList() {

        List<User> found = userService.findAll();
        System.out.println(found);
        assertEquals(1, found.size());
    }

    @Test
    public void FindOne_ReturnsUser() {

        User found = userService.findOne(100);
        assertEquals(100, found.getId());
    }

    @Test
    public void DeleteUser_ValidUserId_ReturnsVoid() {
        assertDoesNotThrow(() -> userService.delete(100));
    }

    @Test
    public void DeleteUser_InvalidUserId_ThrowsNotFoundException() {
        assertThrows(NotFoundException.class, () -> userService.delete(111));
    }

    @Test
    public void DeleteUserByUsername_ValidUserId_ReturnsVoid() {
        assertDoesNotThrow(() -> userService.deleteByUsername("milica"));
    }

    @Test
    public void DeleteUserByUsername_InvalidUserId_ThrowsNotFoundException() {
        assertThrows(NotFoundException.class, () -> userService.deleteByUsername("milicaa"));
    }

    @Test
    public void UpdateSalary_ValidUserId_ReturnsSalary() {
        Salary salary = userService.updateSalary(100, 27000.0);
        assertEquals(27000.0, salary.getValue());
    }

    @Test
    public void UpdateSalary_InvalidUserId_ThrowsNotFoundException() {
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            userService.updateSalary(111, 27000.0);
        });
        assertEquals("User with given id does not exist.", thrown.getMessage());
    }

    @Test
    public void UpdateUser_ValidUser_ReturnsUser() throws Exception {

        User newUser = userService.findOne(100);
        newUser.setName("Novo ime");

        assertEquals(newUser, userService.update(newUser));
    }

    @Test
    public void UpdateUser_InvalidUser_ThrowsNotFoundException() {
        User newUser = new Bartender();
        newUser.setId(111);

        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            userService.update(newUser);
        });
        assertEquals("User with given id does not exist.", thrown.getMessage());
    }

    @Test
    public void CreateUser_ValidUser_ReturnsUser() throws Exception {

        User newUser = new Bartender();
        newUser.setRole(new Role(6,"Bartender"));
        newUser.setId(101);
        newUser.setName("Milan");
        newUser.setLastName("Milovanovic");
        newUser.setEmailAddress("milan@gmail.com");
        newUser.setUsername("milance");
        newUser.setPassword("123");
        newUser.setDeleted(false);

        assertEquals(newUser, userService.create(newUser));
    }

    @Test
    public void CreateUser_InvalidUser_ThrowsDuplicateEntityException() {
        User newUser = new Bartender();
        newUser.setRole(new Role(6,"Bartender"));
        newUser.setId(111);
        newUser.setName("Micko");
        newUser.setLastName("Milovanovic");
        newUser.setEmailAddress("milan@gmail.com");
        newUser.setUsername("milica");
        newUser.setPassword("123");
        newUser.setDeleted(false);
        Salary salary = new Salary();
        salary.setId(1);
        salary.setUser(newUser);
        salary.setValue(25000.0);
        newUser.setSalary(salary);

        DuplicateEntityException thrown = Assertions.assertThrows(DuplicateEntityException.class, () -> {
            userService.create(newUser);
        });
        assertEquals("User with given username already exists.", thrown.getMessage());
    }

    @Test
    public void UpdateDynamicUser_ValidUser_ReturnsUser() throws Exception {

        UserDTO newUser = new UserDTO();
        newUser.setDtype("Bartender");
        newUser.setName("Micko");
        newUser.setLastName("Milovanovic");
        newUser.setEmailAddress("milan@gmail.com");
        newUser.setUsername("milica");
        newUser.setPassword("123");
        newUser.setDeleted(false);
        newUser.setSalary(29000.0);

        assertEquals(newUser.getUsername(), userService.updateDynamicUser(newUser).getUsername());
        assertEquals(29000.0, userService.updateDynamicUser(newUser).getSalary().getValue());
    }

    @Test
    public void UpdateDynamicUser_InvalidUser_ThrowsNotFoundException() {

        UserDTO newUser = new UserDTO();
        newUser.setDtype("Bartender");
        newUser.setName("Micko");
        newUser.setLastName("Milovanovic");
        newUser.setEmailAddress("milan@gmail.com");
        newUser.setUsername("milan");
        newUser.setPassword("123");
        newUser.setDeleted(false);
        newUser.setSalary(29000.0);

        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            userService.updateDynamicUser(newUser);
        });
        assertEquals("User with given username does not exist.", thrown.getMessage());
    }

    @Test
    public void CreateDynamicUser_ValidUser_ReturnsUser() throws Exception {

        UserDTO newUser = new UserDTO();
        newUser.setDtype("Bartender");
        newUser.setName("Micko");
        newUser.setLastName("Milovanovic");
        newUser.setEmailAddress("milan@gmail.com");
        newUser.setUsername("milan");
        newUser.setPassword("123");
        newUser.setDeleted(false);
        newUser.setSalary(29000.0);

        assertEquals("milan", userService.createDynamicUser(newUser).getUsername());
        assertEquals(29000.0, userService.createDynamicUser(newUser).getSalary().getValue());
        assertEquals("milan@gmail.com", userService.createDynamicUser(newUser).getEmailAddress());
    }

    @Test
    public void CreateDynamicUser_InvalidUser_ThrowsDuplicateEntityException() {

        UserDTO newUser = new UserDTO();
        newUser.setDtype("Bartender");
        newUser.setName("Micko");
        newUser.setLastName("Milovanovic");
        newUser.setEmailAddress("milan@gmail.com");
        newUser.setUsername("milica");
        newUser.setPassword("123");
        newUser.setDeleted(false);
        newUser.setSalary(29000.0);

        DuplicateEntityException thrown = Assertions.assertThrows(DuplicateEntityException.class, () -> {
            userService.createDynamicUser(newUser);
        });
        assertEquals("User with given username already exists.", thrown.getMessage());
    }

    @Test
    public void CreateDynamicUser_InvalidUser_ThrowsEmptyParameterException() {

        UserDTO newUser = new UserDTO();
        newUser.setDtype("Bartender");
        newUser.setName("");
        newUser.setLastName("Milovanovic");
        newUser.setEmailAddress("milan@gmail.com");
        newUser.setUsername("milica");
        newUser.setPassword("123");
        newUser.setDeleted(false);
        newUser.setSalary(29000.0);

        EmptyParameterException thrown = Assertions.assertThrows(EmptyParameterException.class, () -> {
            userService.createDynamicUser(newUser);
        });
        assertEquals("Bad input parameters.", thrown.getMessage());
    }

    @Test
    public void HashPassword_InvalidPassword_ThrowsEmptyParameterException() {

        EmptyParameterException thrown = Assertions.assertThrows(EmptyParameterException.class, () -> {
            userService.hashPassword("");
        });
        assertEquals("Invalid password.", thrown.getMessage());
    }
}
