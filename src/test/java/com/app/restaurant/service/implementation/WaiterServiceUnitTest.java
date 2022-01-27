package com.app.restaurant.service.implementation;

import com.app.restaurant.exception.DuplicateEntityException;
import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.*;
import com.app.restaurant.model.enums.ReceiptItemStatus;
import com.app.restaurant.model.users.Bartender;
import com.app.restaurant.model.users.User;
import com.app.restaurant.model.users.Waiter;
import com.app.restaurant.repository.ReceiptRepository;
import com.app.restaurant.repository.WaiterRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WaiterServiceUnitTest {

    @Autowired
    private WaiterService waiterService;

    @MockBean
    private WaiterRepository waiterRepository;
    @MockBean
    private ReceiptRepository receiptRepository;
    @MockBean
    private RestaurantTableService restaurantTableService;
    @MockBean
    private ReceiptService receiptService;
    @MockBean
    private ReceiptItemService receiptItemService;

    @BeforeEach
    public void setUp() {

        List<Waiter> users = new ArrayList<>();
        List<Receipt> receipts = new ArrayList<>();
        Waiter u1 = new Waiter();
        u1.setRole(new Role(7,"Waiter"));
        u1.setId(100);
        u1.setName("Milica");
        u1.setLastName("Mitrovic");
        u1.setEmailAddress("milica@gmail.com");
        u1.setUsername("milica");
        u1.setPassword("123");
        u1.setDeleted(false);

        users.add(u1);

        Receipt receipt = new Receipt();
        receipt.setIssueDate(System.currentTimeMillis());
        receipt.setId(1);

        receipts.add(receipt);

        RestaurantTable restaurantTable = new RestaurantTable();
        restaurantTable.setId(1);

        ReceiptItem receiptItem = new ReceiptItem();

        given(receiptService.findOne(1)).willReturn(receipt);

        given(receiptService.save(receipt)).willReturn(receipt);

        given(waiterRepository.findAll()).willReturn(users);

        given(waiterRepository.findById(100))
                .willReturn(java.util.Optional.of(u1));

        given(waiterRepository.findByUsername("milica")).willReturn(u1);

        given(waiterRepository.save(u1)).willReturn(u1);

        given(restaurantTableService.findOne(1)).willReturn(restaurantTable);

        given(restaurantTableService.save(restaurantTable)).willReturn(restaurantTable);

        given(receiptItemService.save(receiptItem)).willReturn(receiptItem);
    }

    @Test
    public void DeleteWaiter_ValidWaiterId_ReturnsVoid() {
        assertDoesNotThrow(() -> waiterService.delete(100));
    }

    @Test
    public void DeleteWaiter_InvalidWaiterId_ThrowsNotFoundException() {
        assertThrows(NotFoundException.class, () -> waiterService.delete(111));
    }

    @Test
    public void CreateWaiter_ValidWaiter_ReturnsWaiter() throws Exception {

        Waiter newWaiter = new Waiter();
        newWaiter.setRole(new Role(7,"Waiter"));
        newWaiter.setId(111);
        newWaiter.setName("Milica");
        newWaiter.setLastName("Mitrovic");
        newWaiter.setEmailAddress("milica@gmail.com");
        newWaiter.setUsername("milicaa");
        newWaiter.setPassword("123");
        newWaiter.setDeleted(false);

        assertEquals(newWaiter, waiterService.create(newWaiter));
    }

    @Test
    public void CreateWaiter_InvalidWaiter_ThrowsDuplicateEntityException() {

        Waiter newWaiter = waiterService.findOne(100);

        DuplicateEntityException thrown = Assertions.assertThrows(DuplicateEntityException.class, () -> {
            waiterService.create(newWaiter);
        });
        assertEquals("Waiter with given username already exists.", thrown.getMessage());
    }

    @Test
    public void UpdateWaiter_ValidWaiter_ReturnsWaiter() throws Exception {

        Waiter newWaiter = waiterService.findOne(100);
        newWaiter.setLastName("Novoo");

        assertEquals(newWaiter, waiterService.update(newWaiter));
    }

    @Test
    public void UpdateWaiter_InvalidWaiter_ThrowsNotFoundException() {

        Waiter newWaiter = new Waiter();
        newWaiter.setId(111);

        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            waiterService.update(newWaiter);
        });
        assertEquals("Waiter with given username does not exist.", thrown.getMessage());
    }

    @Test
    public void NewReceipt_ValidTableId_ReturnsReceipt() {
        assertDoesNotThrow(() ->  waiterService.newReceipt(1));
    }

    @Test
    public void NewReceipt_InvalidTableId_ReturnsReceipt() {

        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            waiterService.newReceipt(67);
        });
        assertEquals("Table with given id does not exist.", thrown.getMessage());
    }

}
