package com.app.restaurant.controller;

import com.app.restaurant.model.Menu;
import com.app.restaurant.model.MenuItem;
import com.app.restaurant.model.Price;
import com.app.restaurant.model.Receipt;
import com.app.restaurant.service.implementation.MenuItemService;
import com.app.restaurant.service.implementation.ReceiptService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class ReceiptControllerUnitTest {
    @MockBean
    private ReceiptService receiptService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @WithMockUser(username = "ana", password = "123", roles = "WAITER")
    public void GetReceiptById_ValidReceiptId_ReturnsOk() throws IOException {
//        // given
//        given(receiptService.findOne(1))
//                .willReturn(new Receipt(1));
//
//        // when
//        ResponseEntity<Receipt> response = restTemplate.getForEntity("/api/receipts/one-receipt/{id}", Receipt.class, 1);
//        //SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
//        //requestFactory.setOutputStreaming(false);
//        //requestFactory.createRequest(URI.create("http://localhost:8080/api/receipts/one-receipt/1"), HttpMethod.GET);
//
//        // then
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//        Assertions.assertEquals(new Receipt(1), response.getBody());
    }
}
