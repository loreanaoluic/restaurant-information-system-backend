package com.app.restaurant.service.implementation;

import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.*;
import com.app.restaurant.model.enums.ReceiptItemStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class ReceiptServiceIntegrationTest {

    @Autowired
    ReceiptService receiptService;

    @Autowired
    MenuItemService menuItemService;

    @Autowired
    MenuService menuService;

    @Autowired
    PriceService priceService;

    @Autowired
    ReceiptItemService receiptItemService;

    @Test
    public void findAll(){
        List<Receipt> receipts = receiptService.findAll();
        assertEquals(2, receipts.size());
    }

    @Test
    public void testFindById(){
        Receipt r = receiptService.findOne(1);
        assertEquals(1, r.getId());
    }

    @Test
    public void testFindByDate(){
        List<Receipt> receipts = receiptService.findByDate(1637193115);
        assertEquals(2, receipts.size());
    }

    @Test
    public void testSave() throws Exception {
        Receipt r = new Receipt();
        r.setIssueDate(1637193115);
        Item i = menuItemService.findOne(1);
        ReceiptItem receiptItem = new ReceiptItem(1, "staviti vise luka", ReceiptItemStatus.ORDERED , r);
        receiptItem.setItem(i);
        ReceiptItem receiptItem2 = new ReceiptItem(1, "pavlaka u prilog", ReceiptItemStatus.ORDERED, r );
        receiptItem2.setItem(i);
        r.setReceiptItems(new ArrayList<>());
        r.getReceiptItems().add(receiptItem);
        r.getReceiptItems().add(receiptItem2);

        int current = receiptService.findAll().size();
        int current_items = receiptItemService.findAll().size();
        Receipt r2 = receiptService.save(r);
        assertEquals(current+1, receiptService.findAll().size());
        assertEquals(r2.getId(), r.getId());
        assertEquals(current_items+2, receiptItemService.findAll().size());
        assertEquals(r2.getIssueDate(), r.getIssueDate());
        assertEquals(r2.getReceiptItems().size(), r.getReceiptItems().size());

    }


    @Test
    public void testUpdateReceipt() throws Exception {

        Receipt r = receiptService.findOne(1);
        Item i = menuItemService.findOne(1);
        ReceiptItem receiptItem = new ReceiptItem(2, "majonez u prilog", ReceiptItemStatus.ORDERED , r);
        receiptItem.setItem(i);
        int current_size = r.getReceiptItems().size();
        r.getReceiptItems().add(receiptItem);
        receiptService.updateReceipt(r);
        assertEquals(current_size+1, receiptService.findOne(1).getReceiptItems().size());



    }



}
