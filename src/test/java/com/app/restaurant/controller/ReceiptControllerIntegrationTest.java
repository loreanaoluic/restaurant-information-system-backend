package com.app.restaurant.controller;

import com.app.restaurant.dto.ReceiptDTO;
import com.app.restaurant.dto.UserTokenState;
import com.app.restaurant.model.Receipt;
import com.app.restaurant.security.auth.JwtAuthenticationRequest;
import com.app.restaurant.service.implementation.ReceiptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import org.springframework.http.*;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReceiptControllerIntegrationTest {

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;

    @BeforeEach
    public void login() {
        ResponseEntity<UserTokenState> responseEntity =
                restTemplate.postForEntity("/api/auth/login",
                        new JwtAuthenticationRequest("ana", "1234"),
                        UserTokenState.class);
        String accessToken = Objects.requireNonNull(responseEntity.getBody()).getAccessToken();
        headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
    }

    @Test
    public void GetReceiptById_ValidReceiptId_ReturnsOk() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<Receipt> responseEntity =
                restTemplate.exchange("/api/receipts/one-receipt/{id}", HttpMethod.GET, httpEntity, Receipt.class, 1);

        Receipt receipt = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assert receipt != null;
    }

    @Test
    public void UpdateReceipt_ValidReceipt_ReturnsOk() {
        ReceiptDTO receiptDTO = new ReceiptDTO();
        receiptDTO.setId(1);
        receiptDTO.setIssueDate(1637193600);
        HttpEntity<Object> httpEntity = new HttpEntity<>(receiptDTO, headers);
        ResponseEntity<Receipt> responseEntity =
                restTemplate.exchange("/api/receipts/update-receipt", HttpMethod.POST, httpEntity, Receipt.class);

        Receipt receipt = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assert receipt != null;
        assertEquals(1637193600, receipt.getIssueDate());
    }
}
