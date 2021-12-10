package com.app.restaurant.service.implementation;

import com.app.restaurant.model.Receipt;
import com.app.restaurant.model.ReceiptItem;
import com.app.restaurant.model.Role;
import com.app.restaurant.model.enums.ReceiptItemStatus;
import com.app.restaurant.model.users.Waiter;
import com.app.restaurant.repository.ReceiptRepository;
import com.app.restaurant.repository.WaiterRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WaiterServiceUnitTest {

    @Autowired
    private ReceiptService receiptService;
    @Autowired
    private RestaurantTableService restaurantTableService;
    @Autowired
    private WaiterService waiterService;

    @MockBean
    private WaiterRepository waiterRepository;
    @MockBean
    private ReceiptRepository receiptRepository;

    @BeforeEach
    public void setUp() {

        List<Waiter> users = new ArrayList<>();
        List<Receipt> receipts = new ArrayList<>();
        Waiter u1= new Waiter();
        u1.setRole(new Role(7,"Waiter"));
        u1.setId(100);
        u1.setName("Milica");
        u1.setLastName("Mitrovic");
        u1.setEmailAddress("milica@gmail.com");
        u1.setUsername("milica");
        u1.setPassword("123");
        u1.setDeleted(false);

        Waiter u2= new Waiter();
        u2.setRole(new Role(7,"Waiter"));
        u2.setId(100);
        u2.setName("Milica");
        u2.setLastName("Mitrovic");
        u2.setEmailAddress("milica@gmail.com");
        u2.setUsername("milica");
        u2.setPassword("123");
        u2.setDeleted(false);

        users.add(u1);

        Receipt receipt = new Receipt();
        receipt.setIssueDate(System.currentTimeMillis());
        receipt.setId(1);

        receipts.add(receipt);

        given(receiptService.findAll()).willReturn(receipts);

        given(receiptRepository.findById(1)).willReturn(Optional.of(receipt));

        given(waiterRepository.findAll()).willReturn(users);

        given(waiterRepository.findById(100))
                .willReturn(java.util.Optional.of(u1));

        given(waiterRepository.save(u1)).willReturn(u2);
    }

    @Test
    public void newReceipt() {

        Receipt receipt = new Receipt();
        receipt.setIssueDate(System.currentTimeMillis());
        receipt.setId(2);

        List<Receipt> receipts = receiptService.findAll();
        receipts.add(receipt);

        List<Receipt> found = receiptService.findAll();
        assertEquals(2, found.size());
    }

    @Test
    public void newOrder() {

        Optional<Receipt> receipt = receiptRepository.findById(1);

        if(receipt.isPresent()){
            List<ReceiptItem> receiptItems = new ArrayList<>();
            receiptItems.add(new ReceiptItem(1,"poruka", ReceiptItemStatus.ORDERED,new Receipt()));
            receipt.get().setReceiptItems(receiptItems);
            receiptService.save(receipt.get());
        }
        assertEquals(1, receipt.get().getId());
    }
}
