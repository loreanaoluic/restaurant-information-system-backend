package com.app.restaurant.repository;

import com.app.restaurant.model.Receipt;
import com.app.restaurant.model.users.User;
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
public class ReceiptRepositoryTest {

    @Autowired
    private ReceiptRepository receiptRepository;

    @Test
    public void findByDates(){
        List<Receipt> receipst  = receiptRepository.findByDates(1637193114,1637193116);
        assertEquals(1,receipst.size());
    }

    @Test
    public void findByDate(){
        List<Receipt> receipts  = receiptRepository.findByDate(1637193115);
        assertEquals(1,receipts.size());
    }
}
