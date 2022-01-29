package com.app.restaurant.service.implementation;

import com.app.restaurant.exception.DuplicateEntityException;
import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.*;
import com.app.restaurant.model.users.Manager;
import com.app.restaurant.repository.ManagerRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.testng.Assert.assertThrows;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("classpath:application-test.properties")
public class ManagerServiceIntegrationTest {

    @Autowired
    ManagerService managerService;

    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    MenuItemService menuItemService;
    @Autowired
    DrinkCardItemService drinkCardItemService;
    @Autowired
    PriceService priceService;


    @Test
    public void findAll_successfullyFindsAll(){
        List<Manager> allMngrs = this.managerService.findAll();

        for(Manager m : allMngrs) System.out.println(m);

        assertEquals(2, allMngrs.size());
    }

    @Test
    public void findOne_existingId_returnsManager(){
        Manager m = this.managerService.findOne(2);

        assertNotNull(m);
        assertEquals("Dusancic", m.getName());
        assertEquals("dusan@gmail.com", m.getEmailAddress());
    }

    @Test
    public void findOne_nonExistingId_returnsNull(){
        Manager m = this.managerService.findOne(55);

        assertNull(m);
    }

    @Test
    public void save_validManager_returnsSaved(){
        Manager m = new Manager();
        m.setRole(new Role(2, "ROLE_MANAGER"));
        m.setDeleted(false);
        m.setEmailAddress("man@maildrop.cc");
        m.setPassword("sifra");
        m.setUsername("marko");
        m.setName("Marko");
        m.setLastName("Markovic");

        Manager saved = this.managerService.save(m);

        assertNotNull(saved);
        assertEquals(m.getName(), saved.getName());
        assertEquals(m.getEmailAddress(), saved.getEmailAddress());
        assertEquals(m.getUsername(), saved.getUsername());
    }

    @Test
    public void save_missingSomeFields_throwsDataIntegrityViolation(){
        Manager m = new Manager();
        m.setRole(new Role(2, "ROLE_MANAGER"));
        m.setDeleted(false);
        m.setPassword("sifra");
        m.setName("Marko");
        m.setLastName("Markovic");

        DataIntegrityViolationException dive = Assertions.assertThrows(DataIntegrityViolationException.class, ()->this.managerService.save(m));
    }

    @Test
    public void save_nullEntity_throwsInvalidDataAccessApiUsage(){
        InvalidDataAccessApiUsageException idaaue = Assertions.assertThrows(InvalidDataAccessApiUsageException.class, ()->this.managerService.save(null));
        assertEquals("Entity must not be null.; nested exception is java.lang.IllegalArgumentException: Entity must not be null.",
                idaaue.getMessage());
    }


    @Test
    @Order(1)
    public void delete_validId_setsManagerToDeleted(){
        this.managerService.delete(2);

        assertTrue(this.managerService.findOne(2).getDeleted());
    }

    @Test
    @Order(2)
    public void delete_invalidId_throwsNotFoundException(){
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            this.managerService.delete(420);
        });
        assertEquals("Manager with given id does not exist.", thrown.getMessage());
    }


    @Test
    @Order(3)
    public void create_newManager_successfulCreation() throws Exception {
        Manager manager = new Manager();
        manager.setName("Pera");
        manager.setLastName("Peric");
        manager.setEmailAddress("peraperic@maildrop.cc");
        manager.setPassword("1234");
        manager.setUsername("peraperic");
        manager.setRole(new Role(2, "ROLE_MANAGER"));

        assertDoesNotThrow(() -> this.managerService.create(manager));

        assertEquals(1, this.managerService.findAll().size());

        if(this.managerRepository.findByUsername("peraperic") == null) throw new Exception("New manager not found!");
    }

    @Test
    @Order(4)
    public void create_calledTwice_throwsDuplicateException(){
        Manager manager = new Manager();
        manager.setName("Pera");
        manager.setLastName("Pericic");
        manager.setEmailAddress("peraperic@maildrop.cc");
        manager.setPassword("1234");
        manager.setUsername("perapericic");
        manager.setRole(new Role(2, "ROLE_MANAGER"));

        assertDoesNotThrow(() -> this.managerService.create(manager));

        DuplicateEntityException e = Assertions.assertThrows(DuplicateEntityException.class, () -> this.managerService.create(manager));
        assertEquals("Manager already exists.", e.getMessage());
    }

    @Test
    @Order(5)
    public void create_missingEmail_throwsException(){
        Manager manager = new Manager();
        manager.setName("Pera");
        manager.setLastName("Peric");
        manager.setPassword("1234");
        manager.setUsername("peraperic");
        manager.setRole(new Role(2, "ROLE_MANAGER"));

        assertThrows(Exception.class, () -> this.managerService.create(manager));

    }

    @Test
    @Order(6)
    public void update_existingUser_updatesExistingUser(){
        Manager manager = new Manager();
        manager.setId(2);
        manager.setName("Pera");
        manager.setLastName("Peric");
        manager.setEmailAddress("peraperic@maildrop.cc");
        manager.setPassword("1234");
        manager.setUsername("perapericicicicicicic");
        manager.setDeleted(false);
        assertDoesNotThrow(() -> this.managerService.update(manager));

        assertEquals("Pera", this.managerService.findOne(2).getName());
    }

    @Test
    @Order(7)
    public void update_nonExistingUser_throwsNotFoundException(){
        Manager manager = new Manager();
        manager.setId(420);
        manager.setName("Pera");
        manager.setLastName("Peric");
        manager.setEmailAddress("peraperic@maildrop.cc");
        manager.setPassword("1234");
        manager.setUsername("peraperic");
        manager.setRole(new Role(2, "ROLE_MANAGER"));

        NotFoundException e = Assertions.assertThrows(NotFoundException.class, () -> this.managerService.update(manager));
    }

    @Test
    @Order(8)
    public void update_updatingSameUserTwice_updatesExistingUser(){
        Manager manager = new Manager();
        manager.setId(2);
        manager.setName("Pera");
        manager.setLastName("Pericic");
        manager.setEmailAddress("peraperic@maildrop.cc");
        manager.setPassword("1234");
        manager.setUsername("perapericicivivi");
        manager.setDeleted(false);
        manager.setRole(new Role(2, "ROLE_MANAGER"));

        assertDoesNotThrow(() -> this.managerService.update(manager));
        assertDoesNotThrow(() -> this.managerService.update(manager));

        assertEquals("Pera", this.managerService.findOne(2).getName());
    }


    @Test
    @Order(9)
    public void createNewDrinkCardItem_createNewItem_createsProperly(){
        DrinkCardItem dci = new DrinkCardItem();
        dci.setDrinkCard(new DrinkCard(1, new ArrayList<>()));
        dci.setIngredients("Grozdje vrv");
        dci.setName("Penusavo vino");
        dci.setDescription("Pitko");
        dci.setPrice(new Price(0, 0, dci));

        assertDoesNotThrow(() -> this.managerService.createNewDrinkCardItem(dci, 600));

        assertEquals("Penusavo vino", this.drinkCardItemService.findOne(3).getName());
        assertEquals(600, this.drinkCardItemService.findOne(3).getPrice().getValue(), 0);
    }

    @Test
    @Order(10)
    public void createNewDrinkCardItem_createSameItemTwice_producesDuplicate(){
        DrinkCardItem dci = new DrinkCardItem();
        dci.setDrinkCard(new DrinkCard(1, new ArrayList<>()));
        dci.setIngredients("sastojci");
        dci.setName("Viski");
        dci.setDescription("opis");
        dci.setPrice(new Price(0, 0, dci));

        assertDoesNotThrow(() -> this.managerService.createNewDrinkCardItem(dci, 1000));
        assertDoesNotThrow(() -> this.managerService.createNewDrinkCardItem(dci, 1000));

        DrinkCardItem newlyCreated = null;
        int duplicateCounter = 0;

        for(DrinkCardItem d : this.drinkCardItemService.findAll()){
            if(d.getName().equals("Viski") && d.getPrice().getValue() == 1000){
                newlyCreated = d;
                ++duplicateCounter;
            }
        }

        assertNotNull(newlyCreated);
        assertEquals(2, duplicateCounter);
    }

    @Test
    @Order(11)
    public void createNewMenuCardItem_createNewItem_createsProperly(){
        MenuItem mi = new MenuItem();
        mi.setMenu(new Menu(1, new ArrayList<>()));
        mi.setIngredients("Tele vrv");
        mi.setName("Teleca corba");
        mi.setDescription("Opis novog jela");
        mi.setPrice(new Price(0, 0, mi));

        assertDoesNotThrow(() -> this.managerService.createNewMenuItem(mi, 200));

        MenuItem newlyCreated = null;

        for(MenuItem m : this.menuItemService.findAll()){
            if(m.getName().equals("Teleca corba") && m.getPrice().getValue() == 200) newlyCreated = m;
        }

        assertNotNull(newlyCreated);
    }

    @Test
    @Order(12)
    public void createNewMenuCardItem_createSameItemTwice_producesDuplicate(){
        MenuItem mi = new MenuItem();
        mi.setMenu(new Menu(1, new ArrayList<>()));
        mi.setIngredients("Tele vrv");
        mi.setName("Teleca corba");
        mi.setDescription("Opis novog jela");
        mi.setPrice(new Price(0, 0, mi));

        assertDoesNotThrow(() -> this.managerService.createNewMenuItem(mi, 200));
        assertDoesNotThrow(() -> this.managerService.createNewMenuItem(mi, 200));

        MenuItem newlyCreated = null;
        int duplicateCounter = 0;

        for(MenuItem m : this.menuItemService.findAll()){
            if(m.getName().equals("Teleca corba") && m.getPrice().getValue() == 200){
                newlyCreated = m;
                ++duplicateCounter;
            }
        }

        assertNotNull(newlyCreated);
        assertEquals(2, duplicateCounter);
    }

    @Test
    @Order(13)
    public void updateDrinkCardItem_existingItem_updatesItem(){
        DrinkCardItem dci = this.drinkCardItemService.findOne(2);
        dci.setIngredients("Grozdje vrv");
        dci.setName("Penusavo vino");
        dci.setDescription("Pitko");
        dci.getPrice().setValue(1000);

        assertDoesNotThrow(() -> this.managerService.updateDrinkCardItem(dci));

        assertEquals("Penusavo vino", this.drinkCardItemService.findOne(2).getName());
        assertEquals("Grozdje vrv", this.drinkCardItemService.findOne(2).getIngredients());
        assertEquals("Pitko", this.drinkCardItemService.findOne(2).getDescription());
        assertEquals(1000, this.drinkCardItemService.findOne(2).getPrice().getValue(), 0);
    }

    @Test
    @Order(14)
    public void updateDrinkCardItem_updatingSameTwice_updatesItem(){
        DrinkCardItem dci = this.drinkCardItemService.findOne(2);
        dci.setIngredients("Grozdje vrv");
        dci.setName("Penusavo vino");
        dci.setDescription("Pitko");
        dci.getPrice().setValue(1000);

        assertDoesNotThrow(() -> this.managerService.updateDrinkCardItem(dci));
        assertDoesNotThrow(() -> this.managerService.updateDrinkCardItem(dci));

        assertEquals("Penusavo vino", this.drinkCardItemService.findOne(2).getName());
        assertEquals("Grozdje vrv", this.drinkCardItemService.findOne(2).getIngredients());
        assertEquals("Pitko", this.drinkCardItemService.findOne(2).getDescription());
        assertEquals(1000, this.drinkCardItemService.findOne(2).getPrice().getValue(), 0);
    }

    @Test
    @Order(15)
    public void updateDrinkCardItem_invalidId_throwsNotFoundException(){
        DrinkCardItem dci = this.drinkCardItemService.findOne(2);
        dci.setId(55);
        dci.setIngredients("Grozdje vrv");
        dci.setName("Penusavo vino");
        dci.setDescription("Pitko");
        dci.getPrice().setValue(1000);

        NotFoundException e = Assertions.assertThrows(NotFoundException.class, () -> this.managerService.updateDrinkCardItem(dci));

        assertEquals("Drink card item with given id does not exist.", e.getMessage());

        for(MenuItem m : this.menuItemService.findAll()){
            if(m.getName().equals("Penusavo vino") && m.getIngredients().equals("Grozdje vrv")
             && m.getDescription().equals("Pitko") && m.getPrice().getValue() == 1000) fail();
        }
    }

    @Test
    @Order(16)
    public void updateMenuItem_existingItem_updatesItem(){
        MenuItem mi = this.menuItemService.findOne(1);
        mi.setIngredients("Teletina valda");
        mi.setName("Teleca corba");
        mi.setDescription("Opis novog jela");
        mi.getPrice().setValue(250);

        assertDoesNotThrow(() -> this.managerService.updateMenuItem(mi));

        assertEquals("Teleca corba", this.menuItemService.findOne(1).getName());
        assertEquals("Teletina valda", this.menuItemService.findOne(1).getIngredients());
        assertEquals("Opis novog jela", this.menuItemService.findOne(1).getDescription());
        assertEquals(250, this.menuItemService.findOne(1).getPrice().getValue(), 0);
    }

    @Test
    @Order(17)
    public void updateMenuItem_updatingSameTwice_updatesItem(){
        MenuItem mi = this.menuItemService.findOne(1);
        mi.setIngredients("Teletina valda");
        mi.setName("Teleca corba");
        mi.setDescription("Opis novog jela");
        mi.getPrice().setValue(250);

        assertDoesNotThrow(() -> this.managerService.updateMenuItem(mi));
        assertDoesNotThrow(() -> this.managerService.updateMenuItem(mi));

        assertEquals("Teleca corba", this.menuItemService.findOne(1).getName());
        assertEquals("Teletina valda", this.menuItemService.findOne(1).getIngredients());
        assertEquals("Opis novog jela", this.menuItemService.findOne(1).getDescription());
        assertEquals(250, this.menuItemService.findOne(1).getPrice().getValue(), 0);
    }

    @Test
    @Order(18)
    public void updateMenuItem_invalidId_throwsNotFoundException(){
        MenuItem mi = this.menuItemService.findOne(1);
        mi.setId(420);
        mi.setIngredients("Teletina valda");
        mi.setName("Teleca corba");
        mi.setDescription("Opis novog jela");
        mi.getPrice().setValue(250);

        NotFoundException e = Assertions.assertThrows(NotFoundException.class, () -> this.managerService.updateMenuItem(mi));

        assertEquals("Menu item with given id does not exist.", e.getMessage());
        for(MenuItem m : this.menuItemService.findAll()){
            if(m.getName().equals("Penusavo vino") && m.getIngredients().equals("Grozdje vrv")
                    && m.getDescription().equals("Pitko") && m.getPrice().getValue() == 1000) fail();
        }
    }
}
