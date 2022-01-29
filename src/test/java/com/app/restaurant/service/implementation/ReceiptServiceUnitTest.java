package com.app.restaurant.service.implementation;

import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.*;
import com.app.restaurant.model.enums.ReceiptItemStatus;
import com.app.restaurant.repository.ReceiptRepository;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("classpath:application-test.properties")
public class ReceiptServiceUnitTest {

    @Autowired
    ReceiptService receiptService;

    @MockBean
    ReceiptRepository receiptRepository;

    @BeforeEach
    public void setup(){
        List<Receipt> receiptList = new ArrayList<>();
        Receipt receipt = new Receipt(1);

        Price price = new Price();
        price.setValue(240.0);

        ReceiptItem receiptItem = new ReceiptItem(1, 2, "additional_note", ReceiptItemStatus.ORDERED,
                new Receipt(1));
        DrinkCardItem drinkCardItem = new DrinkCardItem();
        drinkCardItem.setPrice(price);
        receiptItem.setItem(drinkCardItem);

        Price price2 = new Price();
        price2.setValue(1280.0);

        ReceiptItem receiptItem2 = new ReceiptItem(2, 1, "additional_note", ReceiptItemStatus.ORDERED,
                new Receipt(1));
        MenuItem menuItem = new MenuItem();
        menuItem.setPrice(price2);
        receiptItem2.setItem(menuItem);

        List<ReceiptItem> receiptItems = new ArrayList<>();
        receiptItems.add(receiptItem);
        receiptItems.add(receiptItem2);

        receipt.setReceiptItems(receiptItems);
        receiptList.add(receipt);

        given(receiptRepository.findAll()).willReturn(receiptList);
        given(receiptRepository.findById(1)).willReturn(java.util.Optional.of(receipt));
        given(receiptRepository.findByDate(1637193115)).willReturn(receiptList);
        given(receiptRepository.findByDates(1637193115, 1637193600)).willReturn(receiptList);
    }

    @Test
    public void FindAllReceiptItems_ValidReceiptId_ReturnsReceiptItemsList(){
        List<ReceiptItem> receiptItems = receiptService.findAllReceiptItems(1);
        assertEquals(2, receiptItems.size());
    }

    @Test
    public void FindAllReceiptItems_InvalidReceiptId_ThrowsNotFoundException() {
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            receiptService.findAllReceiptItems(100);
        });
        assertEquals("Receipt with given id does not exist.", thrown.getMessage());
    }

    @Test
    public void UpdateReceipt_ValidReceipt_ReturnsReceipt() {
        Receipt receipt = new Receipt(1);
        Receipt updatedReceipt = receiptService.updateReceipt(receipt);

        assertEquals(1, updatedReceipt.getId());
    }

    @Test
    public void UpdateReceipt_InvalidReceipt_ThrowsNotFoundException() {
        Receipt receipt = new Receipt(100);

        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            receiptService.updateReceipt(receipt);
        });
        assertEquals("Receipt with given id does not exist.", thrown.getMessage());
    }


    @Test
    public void FindByDate_ReturnsReceiptList(){
        List<Receipt> receipts = receiptService.findByDate(1637193115);
        verify(receiptRepository, times(1)).findByDate(1637193115);
        assertEquals(1, receipts.size());
    }

    @Test
    public void FindByDate_ReturnsEmptyReceiptList() {
        List<Receipt> receipts = receiptService.findByDate(1);
        verify(receiptRepository, times(1)).findByDate(1);
        assertEquals(0, receipts.size());
    }

    @Test
    public void FindByDates_ReturnsReceiptList() {
        List<Receipt> receipts = receiptService.findByDates(1637193115, 1637193600);
        verify(receiptRepository, times(1)).findByDates(1637193115, 1637193600);
        assertEquals(1, receipts.size());
    }

    @Test
    public void FindByDates_ReturnsEmptyReceiptList() {
        List<Receipt> receipts = receiptService.findByDates(1, 1);
        verify(receiptRepository, times(1)).findByDates(1, 1);
        assertEquals(0, receipts.size());
    }

    @Test
    public void CalculateValue_ReceiptList_ReturnsCalculatedValue() {
        assertEquals(1760, receiptService.calculateValue(receiptService.findAll()));
    }

    @Test
    public void CalculateValue_EmptyReceiptList_ReturnsZero() {
        List<Receipt> receipts = new ArrayList<>();
        assertEquals(0, receiptService.calculateValue(receipts));
    }

}
