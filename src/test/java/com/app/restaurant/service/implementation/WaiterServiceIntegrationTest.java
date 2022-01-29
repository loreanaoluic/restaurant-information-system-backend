package com.app.restaurant.service.implementation;

import com.app.restaurant.exception.DuplicateEntityException;
import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.Receipt;
import com.app.restaurant.model.Role;
import com.app.restaurant.model.Salary;
import com.app.restaurant.model.enums.TableStatus;
import com.app.restaurant.model.users.Waiter;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
public class WaiterServiceIntegrationTest {

    @Autowired
    WaiterService waiterService;
    @Autowired
    ReceiptService receiptService;
    @Autowired
    RestaurantTableService restaurantTableService;
    @Autowired
    ReceiptItemService receiptItemService;

    @Test
    public void delete_existingId_deletesWaiter(){
        Waiter w = this.waiterService.findOne(7);
        assertNotNull(w);

        this.waiterService.delete(7);

        assertNull(this.waiterService.findOne(7));
    }

    @Test
    public void delete_nonExistingId_throwsNotFound(){
        NotFoundException nfe = assertThrows(NotFoundException.class,() -> this.waiterService.delete(2));
        assertEquals("Waiter with given id does not exist.", nfe.getMessage());
    }

    @Test
    public void create_newWaiter_returnsCreated(){
        Waiter w = new Waiter();
        w.setDeleted(false);
        w.setEmailAddress("kosta@maildrop.cc");
        w.setLastName("Kostic");
        w.setName("Kosta");
        w.setPassword("sifra");
        w.setRole(new Role(7,"ROLE_WAITER"));
        w.setUsername("kosta");

        assertDoesNotThrow(() ->this.waiterService.create(w));

        Waiter fromDB = this.waiterService.findOne(8);

        assertNotNull(fromDB);
        assertEquals("kosta", fromDB.getUsername());
    }

    @Test
    public void create_existingUsername_throwsDuplicateEntity(){
        Waiter w = new Waiter();
        w.setDeleted(false);
        w.setEmailAddress("kosta@maildrop.cc");
        w.setLastName("Kostic");
        w.setName("Kosta");
        w.setPassword("sifra");
        w.setRole(new Role(7,"ROLE_WAITER"));
        w.setUsername("kosta");

        DuplicateEntityException dee = assertThrows(DuplicateEntityException.class,() ->this.waiterService.create(w));
        assertEquals("Waiter with given username already exists.", dee.getMessage());
    }

    @Test
    public void update_validInfo_returnsUpdated() throws Exception {
        Waiter w = new Waiter();
        w.setDeleted(false);
        w.setEmailAddress("novak@maildrop.cc");
        w.setLastName("Novakovic");
        w.setName("Novak");
        w.setPassword("sifra");
        w.setRole(new Role(7,"ROLE_WAITER"));
        w.setUsername("novak");
        w.setId(7);

        Waiter before = this.waiterService.findOne(7);
        assertEquals("ana@gmail.com", before.getEmailAddress());
        assertEquals("ana", before.getUsername());
        assertEquals("Ana", before.getName());
        assertEquals("Kokic", before.getLastName());


        Waiter after = this.waiterService.update(w);
        assertEquals("novak@maildrop.cc", after.getEmailAddress());
        assertEquals("novak", after.getUsername());
        assertEquals("Novak", after.getName());
        assertEquals("Novakovic", after.getLastName());
    }

    @Test
    public void update_nonExistingId_throwsNotFound(){
        Waiter w = new Waiter();
        w.setDeleted(false);
        w.setEmailAddress("novak@maildrop.cc");
        w.setLastName("Novakovic");
        w.setName("Novak");
        w.setPassword("sifra");
        w.setRole(new Role(7,"ROLE_WAITER"));
        w.setUsername("novak");
        w.setId(77);

        NotFoundException nfe = assertThrows(NotFoundException.class, () -> this.waiterService.update(w));
        assertEquals("Waiter with given id does not exist.", nfe.getMessage());
    }

    @Test
    public void newReceipt_existingTable_returnsCreated(){
        Receipt created = this.waiterService.newReceipt(1);
        assertNotNull(created);
        assertEquals(TableStatus.OCCUPIED,this.restaurantTableService.findOne(1).getTableStatus());
        assertEquals(created.getId(), this.restaurantTableService.findOne(1).getReceipt().getId());
    }

    @Test
    public void newReceipt_creatingTwice_returnsSecondCreated(){
        Receipt created = this.waiterService.newReceipt(1);
        Receipt created2 = this.waiterService.newReceipt(1);
        assertNotNull(created2);
        assertEquals(TableStatus.OCCUPIED,this.restaurantTableService.findOne(1).getTableStatus());
        assertEquals(created2.getId(), this.restaurantTableService.findOne(1).getReceipt().getId());
    }

    @Test
    public void newReceipt_nonExistingTable_throwsNotFound(){
        NotFoundException nfe = assertThrows(NotFoundException.class, ()-> this.waiterService.newReceipt(11));
        assertEquals("Table with given id does not exist.", nfe.getMessage());
    }

    @Test
    public void addItemToReceipt_validItemAndExistingTable_addsToReceipt(){

    }

    @Test
    public void addItemToReceipt_validItemAndNonExistingTable_throwsNotFound(){

    }

    @Test
    public void addItemToReceipt_nullItem_throwsNullPointer(){

    }
}
