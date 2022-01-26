package com.app.restaurant.repository;

import com.app.restaurant.model.Receipt;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class ReceiptRepositoryUnitTest {

    @Autowired
    private ReceiptRepository receiptRepository;

    @Test
    public void FindByDates_FindingAllReceiptsByDates_ReturnsReceiptList(){
        List<Receipt> receipts = receiptRepository.findByDates(1637193114, 1637193116);
        assertEquals(1, receipts.size());
    }

    @Test
    public void FindByDates_FindingAllReceiptsByDates_ReturnsEmptyReceiptList(){
        List<Receipt> receipts = receiptRepository.findByDates(1, 1);
        assertEquals(0, receipts.size());
    }

    @Test
    public void FindByDate_FindingAllReceiptsByDate_ReturnsReceiptList(){
        List<Receipt> receipts = receiptRepository.findByDate(1637193115);
        assertEquals(1, receipts.size());
    }

    @Test
    public void FindByDate_FindingAllReceiptsByDate_ReturnsEmptyReceiptList(){
        List<Receipt> receipts = receiptRepository.findByDate(1);
        assertEquals(0, receipts.size());
    }
}
