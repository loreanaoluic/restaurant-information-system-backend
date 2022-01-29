package com.app.restaurant.service.implementation;

import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.*;
import com.app.restaurant.model.enums.ReceiptItemStatus;
import com.app.restaurant.repository.MenuItemRepository;
import com.app.restaurant.repository.ReceiptItemRepository;
import org.junit.Before;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReceiptItemServiceUnitTest {

    @Autowired
    ReceiptItemService receiptItemService;

    @MockBean
    private ReceiptItemRepository receiptItemRepository;

    @BeforeEach
    public void setUp() {
        List<ReceiptItem> receiptItems = new ArrayList<>();
        ReceiptItem receiptItem = new ReceiptItem(1, 1000, "additional_note", ReceiptItemStatus.ORDERED,
                new Receipt(1));
        receiptItem.setItem(new DrinkCardItem(1, new DrinkCard()));
        ReceiptItem receiptItem2 = new ReceiptItem(3, 1500, "additional_note", ReceiptItemStatus.ORDERED,
                new Receipt(1));
        receiptItem2.setItem(new MenuItem(1, new Menu(), 15.0));
        ReceiptItem receiptItem3 = new ReceiptItem(4, 800, "additional_note", ReceiptItemStatus.READY,
                new Receipt(1));

        receiptItems.add(receiptItem);
        receiptItems.add(receiptItem2);
        receiptItems.add(receiptItem3);

        given(receiptItemRepository.findAll()).willReturn(receiptItems);

        ReceiptItem savedReceiptItem = new ReceiptItem(2, 1500, "additional_note", ReceiptItemStatus.ORDERED,
                new Receipt(1));
        given(receiptItemRepository.findById(1))
                .willReturn(java.util.Optional.of(receiptItem));
        given(receiptItemRepository.findById(4))
                .willReturn(java.util.Optional.of(receiptItem3));

        given(receiptItemRepository.save(receiptItem)).willReturn(savedReceiptItem);

    }

    @Test
    public void UpdateReceiptItemNote_ValidReceiptItemId_ReturnsReceiptItem() {
        ReceiptItem receiptItem = new ReceiptItem(1, 1000, "new_additional_note", ReceiptItemStatus.ORDERED,
                new Receipt(1));
        ReceiptItem updatedReceiptItem = receiptItemService.updateReceiptItemNote(receiptItem);

        assertEquals(1, updatedReceiptItem.getId());
        assertEquals("new_additional_note", receiptItem.getAdditionalNote());
    }

    @Test
    public void UpdateReceiptItemNote_InvalidReceiptItemId_ThrowsNotFoundException() {
        ReceiptItem receiptItem = new ReceiptItem(100, 1000, "new_additional_note", ReceiptItemStatus.ORDERED,
                new Receipt(1));

        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            receiptItemService.updateReceiptItemNote(receiptItem);
        });
        assertEquals("Receipt item with given id does not exist.", thrown.getMessage());
    }

    @Test
    public void ChangeStatusToReady_ValidReceiptItemId_ReturnsReceiptItem() {
        ReceiptItem updatedReceiptItem = receiptItemService.changeStatusToReady(1);

        assertEquals(1, updatedReceiptItem.getId());
        assertEquals(ReceiptItemStatus.READY, updatedReceiptItem.getItemStatus());
    }

    @Test
    public void ChangeStatusToReady_InvalidReceiptItemId_ThrowsNotFoundException() {

        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            receiptItemService.changeStatusToReady(100);
        });
        assertEquals("Receipt item with given id does not exist.", thrown.getMessage());
    }

    @Test
    public void ChangeStatusToDone_ValidReceiptItemId_ReturnsReceiptItem() {
        ReceiptItem updatedReceiptItem = receiptItemService.changeStatusToDone(4);

        assertEquals(4, updatedReceiptItem.getId());
        assertEquals(ReceiptItemStatus.DONE, updatedReceiptItem.getItemStatus());
    }

    @Test
    public void ChangeStatusToDone_InvalidReceiptItemId_ThrowsNotFoundException() {

        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            receiptItemService.changeStatusToDone(100);
        });
        assertEquals("Receipt item with given id does not exist.", thrown.getMessage());
    }

    @Test
    public void CookOrders_ReturnsReceiptItems() {
        assertEquals(1, receiptItemService.cookOrders().size());
    }

    @Test
    public void BartenderOrders_ReturnsReceiptItems() {
        assertEquals(1, receiptItemService.bartenderOrders().size());
    }

    @Test
    public void WaiterOrders_ReturnsReceiptItems() {
        assertEquals(1, receiptItemService.waiterOrders().size());
    }
}
