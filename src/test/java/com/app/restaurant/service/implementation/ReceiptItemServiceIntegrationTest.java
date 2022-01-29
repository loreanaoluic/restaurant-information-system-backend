package com.app.restaurant.service.implementation;

import com.app.restaurant.model.*;
import com.app.restaurant.model.enums.ReceiptItemStatus;
import com.app.restaurant.model.users.Waiter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    public void findAll_successfullyFindsAll(){
        List<ReceiptItem> allRItems = this.receiptItemService.findAll();

        for(ReceiptItem ri : allRItems) System.out.println(ri.getItem().getName());

        assertEquals(3 ,allRItems.size());
    }

    @Test
    public void findOne_existingId_returnsReceiptItem(){
        ReceiptItem ri = this.receiptItemService.findOne(2);

        assertNotNull(ri);
        assertEquals("Pizza", ri.getItem().getName());
        assertEquals(2, ri.getQuantity());
        assertEquals(ReceiptItemStatus.ORDERED, ri.getItemStatus());
    }

    @Test
    public void findOne_nonExistingId_returnsNull(){
        ReceiptItem ri = this.receiptItemService.findOne(22);

        assertNull(ri);
    }

    @Test
    public void save_validReceiptItem_returnsSaved(){
        ReceiptItem ri = new ReceiptItem();
        MenuItem mi = new MenuItem();
        mi.setId(1);
        mi.setMenu(new Menu(1, new ArrayList<>()));
        ri.setItem(mi);
        ri.setReceipt(new Receipt(1));
        ri.setItemStatus(ReceiptItemStatus.ORDERED);
        ri.setQuantity(2);

        Waiter w = new Waiter();
        w.setId(7);
        ri.setAuthor(w);
        ri.setAdditionalNote("napomena");
        ri.setDeleted(false);

        ReceiptItem saved = this.receiptItemService.save(ri);

        assertNotNull(saved);
        assertEquals(ri.getItemStatus(), saved.getItemStatus());
        assertEquals(ri.getQuantity(), saved.getQuantity());
        assertEquals(ri.getAdditionalNote(), saved.getAdditionalNote());
        assertEquals(ri.getReceipt().getId(), saved.getReceipt().getId());
    }

    @Test
    public void save_missingSomeFields_returnsCreated(){
        ReceiptItem ri = new ReceiptItem();

        ri.setItemStatus(ReceiptItemStatus.ORDERED);

        Waiter w = new Waiter();
        w.setId(7);
        ri.setAuthor(w);
        ri.setAdditionalNote("napomena");
        ri.setDeleted(false);

        ReceiptItem saved = this.receiptItemService.save(ri);

        assertNotNull(saved);
        assertEquals(ri.getItemStatus(), saved.getItemStatus());
        assertEquals(ri.getAdditionalNote(), saved.getAdditionalNote());
    }

    @Test
    public void save_nullEntity_throwsInvalidDataAccessApiUsage(){
        InvalidDataAccessApiUsageException idaaue = assertThrows(InvalidDataAccessApiUsageException.class, ()-> this.receiptItemService.save(null));
        assertEquals("Entity must not be null.; nested exception is java.lang.IllegalArgumentException: Entity must not be null.",
                idaaue.getMessage());
    }

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
        assertEquals(3, receiptItems.size());
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
