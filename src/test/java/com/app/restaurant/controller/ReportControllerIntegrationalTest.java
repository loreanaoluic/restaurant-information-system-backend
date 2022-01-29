package com.app.restaurant.controller;

import com.app.restaurant.dto.ExpenseDTO;
import com.app.restaurant.dto.ReportDTO;
import com.app.restaurant.dto.UserTokenState;
import com.app.restaurant.security.auth.JwtAuthenticationRequest;
import com.app.restaurant.service.implementation.ExpenseService;
import com.app.restaurant.service.implementation.ReceiptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@Transactional
public class ReportControllerIntegrationalTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    ReceiptService receiptService;

    @Autowired
    ExpenseService expenseService;

    private HttpHeaders headers;

    @BeforeEach
    public void Login() {

        ResponseEntity<UserTokenState> responseEntity =
                restTemplate.postForEntity("/api/auth/login",
                        new JwtAuthenticationRequest("dusan", "1234"),
                        UserTokenState.class);

        String accessToken = Objects.requireNonNull(responseEntity.getBody()).getAccessToken();
        headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

    }

    @Test
    public void GetAll_ReturnsOk(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<ReportDTO> responseEntity = restTemplate.exchange("/api/reports/", HttpMethod.GET, httpEntity, ReportDTO.class );

        ReportDTO report = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(40690, report.getExpense());
        assertEquals(2300, report.getIncome());

    }

    @Test
    public void GetByDates_ValidDates_ReturnsOk(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<ReportDTO> responseEntity = restTemplate.exchange("/api/reports/1637193115/1637193600", HttpMethod.GET, httpEntity, ReportDTO.class );

        ReportDTO report = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(40690, report.getExpense());
        assertEquals(2300, report.getIncome());
    }

    @Test
    public void GetByDates_InvalidStartDate_ReturnsBadRequest(){
        //DATUM U BUDUCNOSTI
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<?> responseEntity = restTemplate.exchange("/api/expense/2648467317123/1637193600", HttpMethod.GET, httpEntity, ResponseEntity.class );

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void GetByDateS_StartDateGraterThanEndDate_ReturnsBadRequest(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<?> responseEntity = restTemplate.exchange("/api/reports/1637193600/1637193115", HttpMethod.GET, httpEntity, ResponseEntity.class );

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

}
