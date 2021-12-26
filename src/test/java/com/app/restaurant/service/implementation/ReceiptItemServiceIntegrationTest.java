package com.app.restaurant.service.implementation;

import com.app.restaurant.model.Item;
import com.app.restaurant.model.Receipt;
import com.app.restaurant.model.ReceiptItem;
import com.app.restaurant.model.enums.ReceiptItemStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")

public class ReceiptItemServiceIntegrationTest {

    @Autowired
    ReceiptItemService receiptItemService;

    @Autowired
    ReceiptService receiptService;
    @Autowired
    MenuItemService menuItemService;
    @Test
    public void testFindAll(){
        List<ReceiptItem> receiptItemServiceList = receiptItemService.findAll();
        assertEquals(2, receiptItemServiceList.size());
    }

    @Test
    public void testChangeStatus() throws Exception {
        ReceiptItem r = receiptItemService.findOne(1);
        r.setItemStatus(ReceiptItemStatus.READY);
        receiptItemService.changeStatusToReady(r.getId());
        assertEquals(ReceiptItemStatus.READY, receiptItemService.findOne(1).getItemStatus());
    }

    @Test
    public void testCookOrders(){
        List<ReceiptItem> receiptItems = receiptItemService.cookOrders();
        assertEquals(2, receiptItems.size());
    }

    @Test
    public void testBartenderOrders(){
        List<ReceiptItem> receiptItems = receiptItemService.bartenderOrders();
        assertEquals(0, receiptItems.size());
    }

    @Test
    public void testSave(){
        Receipt r = receiptService.findOne(1);
        Item i = menuItemService.findOne(1);
        ReceiptItem receiptItem = new ReceiptItem(1, "staviti vise luka", ReceiptItemStatus.ORDERED , r);
        receiptItem.setItem(i);
        int current_receipts_items = receiptItemService.findAll().size();
        int current_receipts_items_receipt = r.getReceiptItems().size();
        receiptItemService.save(receiptItem);
        ++current_receipts_items;
        ++current_receipts_items_receipt;
        assertEquals(current_receipts_items, receiptItemService.findAll().size());
        Receipt r2 = receiptService.findOne(1);
        assertEquals(current_receipts_items_receipt, r2.getReceiptItems().size());
    }


}
