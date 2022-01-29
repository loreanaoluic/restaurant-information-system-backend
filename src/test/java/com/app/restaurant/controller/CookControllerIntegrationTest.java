package com.app.restaurant.controller;

import com.app.restaurant.dto.ExpenseDTO;
import com.app.restaurant.dto.ReceiptItemDTO;
import com.app.restaurant.dto.UserTokenState;
import com.app.restaurant.model.Expense;
import com.app.restaurant.model.ReceiptItem;
import com.app.restaurant.model.enums.ReceiptItemStatus;
import com.app.restaurant.security.auth.JwtAuthenticationRequest;
import com.app.restaurant.service.implementation.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@Transactional
public class CookControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    ExpenseService expenseService;

    private HttpHeaders headers;

    @BeforeEach
    public void login() {

        ResponseEntity<UserTokenState> responseEntity =
                restTemplate.postForEntity("/api/auth/login",
                        new JwtAuthenticationRequest("mladen", "1234"),
                        UserTokenState.class);

        String accessToken = Objects.requireNonNull(responseEntity.getBody()).getAccessToken();
        headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

    }

    @Test
    public void GetOrders_ReturnsOk(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<ReceiptItemDTO[]> responseEntity = restTemplate.exchange("/api/cook/orders", HttpMethod.GET, httpEntity, ReceiptItemDTO[].class );

        ReceiptItemDTO[] receiptItems = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void ChangeStatus_ValidReceiptItem_ReturnsOk(){
        HttpEntity<ReceiptItemDTO> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<ReceiptItemDTO> responseEntity = restTemplate.exchange("/api/cook/1/change-status", HttpMethod.POST, httpEntity, ReceiptItemDTO.class );

        ReceiptItemDTO receiptItem = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(ReceiptItemStatus.READY, receiptItem.getItemStatus());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void ChangeStatus_InvalidReceiptItem_ReturnsBadRequest(){
        HttpEntity<ReceiptItemDTO> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<ReceiptItemDTO> responseEntity = restTemplate.exchange("/api/cook/3/change-status", HttpMethod.POST, httpEntity, ReceiptItemDTO.class );

        ReceiptItemDTO receiptItem = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
