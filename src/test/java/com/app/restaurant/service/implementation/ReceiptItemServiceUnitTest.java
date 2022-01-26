package com.app.restaurant.service.implementation;

import com.app.restaurant.model.MenuItem;
import com.app.restaurant.model.Price;
import org.junit.Before;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.when;

public class ReceiptItemServiceUnitTest {

    @Autowired
    ReceiptItemService receiptItemService;
    @Autowired
    ReceiptService receiptService;
    @Autowired
    MenuItemService menuItemService;

    @Before
    public void setUp(){

    }
}
