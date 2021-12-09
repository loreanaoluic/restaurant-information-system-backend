package com.app.restaurant.service.implementation;

import com.app.restaurant.model.*;
import com.app.restaurant.model.enums.ReceiptItemStatus;
import com.app.restaurant.repository.ReceiptRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
    public  void setup(){
        List<Receipt> receiptList = new ArrayList<>();
        Receipt r = new Receipt();
        r.setId(1);
//        r.setIssueDate(1637193115);
//        Item i = new MenuItem(1, "cevapi", "mljeveno meso, lepina", "","bosanska hrana", new Price(1, 500,1637193115, -1 ), null,30 );
//        ReceiptItem receiptItem = new ReceiptItem(1, "staviti vise luka", ReceiptItemStatus.ORDERED , r);
//        receiptItem.setItem(i);
        r.setReceiptItems(new ArrayList<>());
        //r.getReceiptItems().add(receiptItem);
        receiptList.add(r);
        given(receiptRepository.findAll()).willReturn(receiptList);
        given(receiptRepository.findById(1)).willReturn(java.util.Optional.of(r));
        given(receiptRepository.findByDate(1637193115)).willReturn(receiptList);
        given(receiptRepository.findByDates(1637193115, 1637193600)).willReturn(receiptList);


    }

    @Test
    public void testFindById(){
        Receipt r = receiptService.findOne(1);
        verify(receiptRepository, times(1)).findById(1);
        assertEquals(1, r.getId());
    }

    @Test
    public void testFindByDate(){
        List<Receipt> receipts = receiptService.findByDate(1637193115);
        verify(receiptRepository, times(1)).findByDate(1637193115);
        assertEquals(1, receipts.size());
    }

    @Test
    public void testFindByDates(){
        List<Receipt> receipts = receiptService.findByDates(1637193115, 1637193600);
        verify(receiptRepository, times(1)).findByDates(1637193115, 1637193600);
        assertEquals(1, receipts.size());
    }

    @Test
    public void testFindAll(){
        List<Receipt> receipts = receiptService.findAll();
        verify(receiptRepository, times(1)).findAll();
        assertEquals(1, receipts.size());
    }
}
