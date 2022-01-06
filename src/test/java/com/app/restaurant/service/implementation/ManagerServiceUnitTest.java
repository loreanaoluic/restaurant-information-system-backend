package com.app.restaurant.service.implementation;

import com.app.restaurant.exception.DuplicateEntityException;
import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.*;
import com.app.restaurant.model.users.Manager;
import com.app.restaurant.repository.ManagerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("classpath:application-test.properties")
public class ManagerServiceUnitTest {

    @Autowired
    ManagerService managerService;

    @MockBean
    ManagerRepository managerRepository;
    @MockBean
    MenuItemService menuItemService;
    @MockBean
    DrinkCardItemService drinkCardItemService;
    @MockBean
    PriceService priceService;


    @Before
    public void setUp(){
        Manager manager = new Manager();
        manager.setRole(new Role(2, "ROLE_MANAGER"));
        manager.setName("Pera");
        manager.setLastName("Peric");
        manager.setUsername("peraperic");
        manager.setPassword("password");
        manager.setDeleted(false);
        manager.setId(15);
        manager.setSalary(new Salary(50000d, System.currentTimeMillis(), manager));


        when(this.managerRepository.findById(15)).thenReturn(java.util.Optional.of(manager));
        when(this.managerRepository.findByUsername("peraperic")).thenReturn(manager);
        when(this.managerRepository.save(Mockito.any(Manager.class))).thenAnswer(i -> i.getArguments()[0]);

        Menu menu = new Menu(1, new ArrayList<>());
        DrinkCard drinkCard = new DrinkCard(1, new ArrayList<>());

        DrinkCardItem dci = new DrinkCardItem();
        dci.setName("Penusavo vino");
        dci.setDescription("Pitko");
        dci.setIngredients("Grozdje vrv");
        dci.setId(3);
        dci.setDrinkCard(drinkCard);
        dci.setPrice(new Price(1000, System.currentTimeMillis(), dci));
        dci.getPrice().setId(3);

        MenuItem mi = new MenuItem();
        mi.setName("Teleca corba");
        mi.setDescription("Ukusna");
        mi.setIngredients("Tele vrv");
        mi.setId(2);
        mi.setMenu(menu);
        mi.setPreparationTime(15);
        mi.setPrice(new Price(250, System.currentTimeMillis(), mi));
        mi.getPrice().setId(2);

        when(this.menuItemService.findOne(2)).thenReturn(mi);
        when(this.drinkCardItemService.findOne(3)).thenReturn(dci);
        when(this.drinkCardItemService.save(Mockito.any(DrinkCardItem.class))).thenAnswer(i -> i.getArguments()[0]);
        when(this.menuItemService.save(Mockito.any(MenuItem.class))).thenAnswer(i -> i.getArguments()[0]);

        when(this.priceService.save(Mockito.any(Price.class))).thenAnswer(i -> i.getArguments()[0]);
        when(this.priceService.findOne(2)).thenReturn(mi.getPrice());
        when(this.priceService.findOne(3)).thenReturn(dci.getPrice());
    }

    @Test
    public void delete_validId_setsDeletedToTrue(){
        assertDoesNotThrow(() -> this.managerService.delete(15));
    }

    @Test
    public void delete_nonExistingId_throwsNotFound(){
        NotFoundException nfe = assertThrows(NotFoundException.class, () -> this.managerService.delete(55));
    }

    @Test
    public void create_newManager_returnsManager() throws Exception {
        Manager manager = this.managerRepository.findByUsername("peraperic");
        manager.setUsername("zikazikic");
        assertEquals(manager, this.managerService.create(manager));
        assertDoesNotThrow(() -> this.managerService.save(manager));
    }

    @Test
    public void create_existingManager_throwsDuplicateEntity(){
        Manager manager = this.managerRepository.findByUsername("peraperic");
        DuplicateEntityException dee = assertThrows(DuplicateEntityException.class, () -> this.managerService.create(manager));

        assertEquals("Manager already exists.", dee.getMessage());
    }

    @Test
    public void update_existingManager_updatesProperly() throws Exception {
        Manager manager = this.managerRepository.findByUsername("peraperic");

        assertDoesNotThrow(() -> this.managerService.update(manager));
        assertEquals(manager, this.managerService.update(manager));
    }

    @Test
    public void update_nonExistingManager_throwsNotFound() throws Exception {
        Manager manager = this.managerRepository.findByUsername("peraperic");
        manager.setId(2);

        NotFoundException nfe = assertThrows(NotFoundException.class, () -> this.managerService.update(manager));
        assertEquals("Manager does not exist.", nfe.getMessage());
    }

    @Test
    public void createNewDrinkCardItem_newDrinkItem_returnsCreated(){
        DrinkCardItem dci = this.drinkCardItemService.findOne(3);
        assertNotNull(this.managerService.createNewDrinkCardItem(dci, 1000));
        assertEquals(dci, this.managerService.createNewDrinkCardItem(dci, 1000));
    }

    @Test
    public void createNewMenuItem_newMenu_returnsCreated(){
        MenuItem mi = this.menuItemService.findOne(2);
        assertNotNull(this.managerService.createNewMenuItem(mi, 250));
        assertEquals(mi, this.managerService.createNewMenuItem(mi, 250));
    }

    @Test
    public void updateDrinkCardItem_nonExistingPrice_throwsNotFound(){
        DrinkCardItem dci = this.drinkCardItemService.findOne(3);
        dci.getPrice().setId(9);

        NotFoundException nfe = assertThrows(NotFoundException.class, () -> this.managerService.updateDrinkCardItem(dci));

        assertEquals("Price with given id does not exist.", nfe.getMessage());
    }

    @Test
    public void updateDrinkCardItem_validInfo_returnsUpdated() throws Exception {
        DrinkCardItem dci = this.drinkCardItemService.findOne(3);
        System.out.println(dci.getPrice().getId());
        dci.getPrice().setId(3);
        assertDoesNotThrow(() -> this.managerService.updateDrinkCardItem(dci));

        assertEquals(dci, this.managerService.updateDrinkCardItem(dci));
    }

    @Test
    public void updateMenuItem_nonExistingPrice_throwsNotFound(){
        MenuItem mi = this.menuItemService.findOne(2);
        mi.getPrice().setId(9);

        NotFoundException nfe = assertThrows(NotFoundException.class, () -> this.managerService.updateMenuItem(mi));

        assertEquals("Price with given id does not exist.", nfe.getMessage());
    }

    @Test
    public void updateMenuItem_validInfo_returnsUpdated() throws Exception {
        MenuItem mi = this.menuItemService.findOne(2);
        assertDoesNotThrow(() -> this.managerService.updateMenuItem(mi));

        assertEquals(mi, this.managerService.updateMenuItem(mi));
    }
}
