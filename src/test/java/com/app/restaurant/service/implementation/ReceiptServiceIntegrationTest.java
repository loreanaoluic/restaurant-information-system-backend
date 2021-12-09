package com.app.restaurant.service.implementation;

import com.app.restaurant.model.Receipt;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

    @Test
    public void findAll(){
        List<Receipt> receipts = receiptService.findAll();
        assertEquals(1, receipts.size());
    }

    @Test
    public void testFindById(){
        Receipt r = receiptService.findOne(1);
        assertEquals(1, r.getId());
    }

    @Test
    public void testFindByDate(){
        List<Receipt> receipts = receiptService.findByDate(1637193115);
        assertEquals(1, receipts.size());
    }



}
