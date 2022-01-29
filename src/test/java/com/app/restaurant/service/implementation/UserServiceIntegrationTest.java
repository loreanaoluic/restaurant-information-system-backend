package com.app.restaurant.service.implementation;

import com.app.restaurant.dto.UserDTO;
import com.app.restaurant.exception.DuplicateEntityException;
import com.app.restaurant.exception.EmptyParameterException;
import com.app.restaurant.exception.InvalidValueException;
import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.Role;
import com.app.restaurant.model.users.Cook;
import com.app.restaurant.model.users.User;
import com.app.restaurant.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("classpath:application-test.properties")
public class UserServiceIntegrationTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SalaryService salaryService;

    @Test
    public void loadUserByUsername_existingUsername_returnsUser(){
        UserDetails u = this.userService.loadUserByUsername("dusan");

        assertEquals("dusan", u.getUsername());

        assertDoesNotThrow(()-> this.userService.loadUserByUsername("dusan"));
    }

    @Test
    public void loadUserByUsername_nonExistingUsername_throwsNotFound(){
        NotFoundException nfe = assertThrows(NotFoundException.class, () -> this.userService.loadUserByUsername("dddddd"));
        assertEquals("No user found for "+ "dddddd" + ".", nfe.getMessage());
    }

    @Test
    public void delete_validId_setsDeletedToTrue(){
        this.userService.delete(7);
        User u = this.userService.findOne(7);

        assertTrue(u.getDeleted());
        assertEquals((Integer)7, u.getId());
    }

    @Test
    public void delete_invalidId_throwsNotFound(){
        NotFoundException nfe = assertThrows(NotFoundException.class, () -> this.userService.delete(555));
        assertEquals("User with given id does not exist.", nfe.getMessage());
    }

    @Test
    public void deleteByUsername_existingUsername_setsDeletedToTrue(){
        this.userService.deleteByUsername("milica");
        User u = this.userService.findByUsername("milica");

        assertNull(u);
    }

    @Test
    public void deleteByUsername_nonExistingUsername_throwsNotFound(){
        NotFoundException nfe = assertThrows(NotFoundException.class, () -> this.userService.deleteByUsername("janko"));
        assertEquals("User with given username does not exist.", nfe.getMessage());
    }


    @Test
    public void updateSalary_existingId_returnsNewSalary(){
        User uBefore = this.userService.findOne(2);

        assertEquals( 50000, uBefore.getSalary().getValue(), 0);

        this.userService.updateSalary(2, 77777);

        User uAfter = this.userService.findOne(2);

        assertEquals( 77777, uAfter.getSalary().getValue(), 0);
    }

    @Test
    public void updateSalary_nonExistingId_throwsNotFound(){
        NotFoundException nfe = assertThrows(NotFoundException.class, ()-> this.userService.updateSalary(22, 77777));
        assertEquals("User with given id does not exist.", nfe.getMessage());
    }

    @Test
    public void updateSalary_sameSalary_returnsOldSalary(){
        User uBefore = this.userService.findOne(2);

        assertEquals( 77777, uBefore.getSalary().getValue(), 0);

        this.userService.updateSalary(2, 77777);

        User uAfter = this.userService.findOne(2);

        assertEquals( 77777, uAfter.getSalary().getValue(), 0);
    }

    @Test
    public void updateSalary_invalidSalary_throwsInvalidValue(){
        InvalidValueException ive = assertThrows(InvalidValueException.class, ()-> this.userService.updateSalary(2, -77777));
        assertEquals("Salary cannot be a negative value.", ive.getMessage());
    }

    @Test
    public void update_existingId_returnsUpdated(){
        User newInfo = this.userService.findOne(2);
        newInfo.setName("Dusancic");
        newInfo.setRole(new Role(4, "ROLE_COOK"));

        assertDoesNotThrow(() -> assertEquals(newInfo, this.userService.update(newInfo)));

        assertEquals("Dusancic", this.userService.findOne(2).getName());
        assertEquals("ROLE_COOK", this.userService.findOne(2).getRole().getName());
    }

    @Test
    public void update_invalidId_throwsNotFound(){
        User newInfo = this.userService.findOne(2);
        newInfo.setId(777);
        newInfo.setName("Dusancic");
        newInfo.setRole(new Role(4, "ROLE_COOK"));

        NotFoundException nfe = assertThrows(NotFoundException.class ,() -> this.userService.update(newInfo));
        assertEquals("User with given id does not exist.", nfe.getMessage());
    }

    @Test
    public void create_newUser_returnsCreated(){
        User newUser = new Cook();
        newUser.setUsername("janko");
        newUser.setRole(new Role(4, "ROLE_COOK"));
        newUser.setName("Janko");
        newUser.setLastName("Jankovic");
        newUser.setEmailAddress("janko@maildrop.cc");
        newUser.setPassword("sifra");

        assertDoesNotThrow(() -> assertEquals(newUser, this.userService.create(newUser)));

        User newFromDB = this.userService.findByUsername("janko");

        assertNotNull(newFromDB);
        assertEquals("Janko", newFromDB.getName());
    }

    @Test
    public void create_missingEmail_throwsDataIntegrityViolation(){
        User newUser = new Cook();
        newUser.setUsername("janko");
        newUser.setRole(new Role(4, "ROLE_COOK"));
        newUser.setName("Janko");
        newUser.setLastName("Jankovic");

        assertThrows(DataIntegrityViolationException.class, () -> this.userService.create(newUser));
    }

    @Test
    public void create_existingUsername_throwsDuplicateEntity(){
        User newUser = new Cook();
        newUser.setUsername("dusan");
        newUser.setRole(new Role(4, "ROLE_COOK"));
        newUser.setName("Janko");
        newUser.setLastName("Jankovic");

        DuplicateEntityException dee = assertThrows(DuplicateEntityException.class, () -> this.userService.create(newUser));
        assertEquals("User with given username already exists.", dee.getMessage());
    }

    @Test
    public void updateDynamicUser_validUpdate_returnsUpdated() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setUsername("nemanja");
        dto.setName("Nemanja");
        dto.setLastName("Mi");
        dto.setEmailAddress("n@maildrop.cc");
        dto.setPassword("sifra");
        dto.setDtype("Manager");

        User beforeUpdate = this.userService.findByUsername("nemanja");

        User afterUpdate = this.userService.updateDynamicUser(dto);

        assertEquals("Nelenzi", beforeUpdate.getName());
        assertEquals("M", beforeUpdate.getLastName());
        assertEquals("novimejl@maildrop.cc", beforeUpdate.getEmailAddress());


        assertEquals("Nemanja", afterUpdate.getName());
        assertEquals("Mi", afterUpdate.getLastName());
        assertEquals("n@maildrop.cc", afterUpdate.getEmailAddress());

    }

    @Test
    public void updateDynamicUser_updatingTwice_returnsUpdated() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setUsername("nemanja");
        dto.setName("Nelenzi");
        dto.setLastName("M");
        dto.setEmailAddress("novimejl@maildrop.cc");
        dto.setPassword("sifra");
        dto.setDtype("Manager");

        User beforeUpdate = this.userService.findByUsername("nemanja");

        User afterUpdate = this.userService.updateDynamicUser(dto);
        afterUpdate = this.userService.updateDynamicUser(dto);

        assertEquals("Nemanja", beforeUpdate.getName());
        assertEquals("Milutinovic", beforeUpdate.getLastName());
        assertEquals("nemanja@gmail.com", beforeUpdate.getEmailAddress());


        assertEquals("Nelenzi", afterUpdate.getName());
        assertEquals("M", afterUpdate.getLastName());
        assertEquals("novimejl@maildrop.cc", afterUpdate.getEmailAddress());

    }

    @Test
    public void updateDynamicUser_nonExistingUsername_throwsNotFound(){
        UserDTO dto = new UserDTO();
        dto.setUsername("nepostojeci");
        dto.setName("Nelenzi");
        dto.setLastName("M");
        dto.setEmailAddress("novimejl@maildrop.cc");
        dto.setPassword("sifra");
        dto.setDtype("Manager");

        NotFoundException nfe = assertThrows(NotFoundException.class, () -> this.userService.updateDynamicUser(dto));
        assertEquals("User with given username does not exist.", nfe.getMessage());
    }

    @Test
    public void updateDynamicUser_invalidSalary_throwsInvalidValue(){
        UserDTO dto = new UserDTO();
        dto.setUsername("nemanja");
        dto.setName("Nelenzi");
        dto.setLastName("M");
        dto.setEmailAddress("novimejl@maildrop.cc");
        dto.setPassword("sifra");
        dto.setDtype("Manager");
        dto.setSalary(-55555);

        InvalidValueException ive = assertThrows(InvalidValueException.class, () -> this.userService.updateDynamicUser(dto));
        assertEquals("Salary may not be negative.", ive.getMessage());
    }

    @Test
    public void createDynamicUser_validNewUser_returnsCreated() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setUsername("novak");
        dto.setName("Novak");
        dto.setLastName("Novakovic");
        dto.setEmailAddress("novak@maildrop.cc");
        dto.setPassword("sifra");
        dto.setDtype("Bartender");
        dto.setSalary(45000);
        dto.setDeleted(false);

        User created = this.userService.createDynamicUser(dto);

        assertEquals(dto.getUsername(), created.getUsername());
        assertEquals(dto.getName(), created.getName());
        assertEquals(dto.getLastName(), created.getLastName());
        assertEquals(dto.getEmailAddress(), created.getEmailAddress());
        assertEquals(dto.getSalary(), created.getSalary().getValue(), 0);
        assertEquals(dto.getDtype(), created.getRole().getName());
    }

    @Test
    public void createDynamicUser_missingFields_throwsEmptyParameter() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setUsername("novak");
        dto.setName("Novak");
        dto.setLastName("");
        dto.setEmailAddress("novak@maildrop.cc");
        dto.setPassword("sifra");
        dto.setDtype("Bartender");
        dto.setSalary(45000);
        dto.setDeleted(false);

        EmptyParameterException epe = assertThrows(EmptyParameterException.class, () -> this.userService.createDynamicUser(dto));
        assertEquals("Bad input parameters.", epe.getMessage());
    }

    @Test
    public void createDynamicUser_existingUsername_throwsDuplicateEntity(){
        UserDTO dto = new UserDTO();
        dto.setUsername("nemanja");
        dto.setName("Novak");
        dto.setLastName("Novakovic");
        dto.setEmailAddress("novak@maildrop.cc");
        dto.setPassword("sifra");
        dto.setDtype("Bartender");
        dto.setSalary(45000);
        dto.setDeleted(false);

        DuplicateEntityException dee = assertThrows(DuplicateEntityException.class, () -> this.userService.createDynamicUser(dto));
        assertEquals("User with given username already exists.", dee.getMessage());
    }

    @Test
    public void createDynamicUser_missingFields_throwsInvalidValue() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setUsername("novak");
        dto.setName("Novak");
        dto.setLastName("Novakovic");
        dto.setEmailAddress("novak@maildrop.cc");
        dto.setPassword("sifra");
        dto.setDtype("Bartender");
        dto.setSalary(-45000);
        dto.setDeleted(false);

        InvalidValueException ive = assertThrows(InvalidValueException.class, () -> this.userService.createDynamicUser(dto));
        assertEquals("Salary may not be negative.", ive.getMessage());
    }
}
