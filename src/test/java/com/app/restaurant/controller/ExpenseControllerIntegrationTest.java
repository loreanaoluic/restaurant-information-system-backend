package com.app.restaurant.controller;

import com.app.restaurant.dto.ExpenseDTO;
import com.app.restaurant.dto.UserTokenState;
import com.app.restaurant.exception.InvalidValueException;
import com.app.restaurant.model.Expense;
import com.app.restaurant.security.auth.JwtAuthenticationRequest;
import com.app.restaurant.service.implementation.ExpenseService;
import com.app.restaurant.service.implementation.UserService;
import org.junit.jupiter.api.BeforeAll;
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
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@Transactional
public class ExpenseControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    ExpenseService expenseService;

    private HttpHeaders headers;

    @BeforeEach
    public void login() {

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
        ResponseEntity<ExpenseDTO[]> responseEntity = restTemplate.exchange("/expenses/", HttpMethod.GET, httpEntity, ExpenseDTO[].class );

        ExpenseDTO[] expenses = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("nabavka salate", expenses[0].getText());
        assertEquals(2000, expenses[0].getValue());
        assertEquals(1637193115, expenses[0].getDate());

        assertEquals(2, expenses.length);

    }

    @Test
    public void GetByDate_ValidDate_ReturnsOk(){

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<ExpenseDTO[]> responseEntity = restTemplate.exchange("/expenses/date/1637193115", HttpMethod.GET, httpEntity, ExpenseDTO[].class );

        ExpenseDTO[] expenses = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("nabavka salate", expenses[0].getText());
        assertEquals(2000, expenses[0].getValue());
        assertEquals(1637193115, expenses[0].getDate());
        assertEquals(1, expenses.length);
    }

    @Test
    public void GetByDate_InvalidDate_ReturnsBadRequest(){
        //DATUM U BUDUCNOSTI
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<?> responseEntity = restTemplate.exchange("/expenses/date/2648467317123", HttpMethod.GET, httpEntity, ResponseEntity.class );

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void GetByDates_ValidDates_ReturnsOk(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<ExpenseDTO[]> responseEntity = restTemplate.exchange("/expenses/1637193115/1637193600", HttpMethod.GET, httpEntity, ExpenseDTO[].class );

        ExpenseDTO[] expenses = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("nabavka salate", expenses[0].getText());
        assertEquals(2000, expenses[0].getValue());
        assertEquals(1637193115, expenses[0].getDate());
        assertEquals("nabavka makarona", expenses[1].getText());
        assertEquals(2690, expenses[1].getValue());
        assertEquals(1637193600, expenses[1].getDate());
        assertEquals(2, expenses.length);
    }

    @Test
    public void GetByDates_InvalidStartDate_ReturnsBadRequest(){
        //DATUM U BUDUCNOSTI
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<?> responseEntity = restTemplate.exchange("/expenses/2648467317123/1637193600", HttpMethod.GET, httpEntity, ResponseEntity.class );

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void GetByDateS_StartDateGraterThanEndDate_ReturnsBadRequest(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<?> responseEntity = restTemplate.exchange("/expenses/1637193600/1637193115", HttpMethod.GET, httpEntity, ResponseEntity.class );

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void UpdateExpense_ValidId_ReturnsOk(){
        Expense e = expenseService.findOne(1);
        int current_size = expenseService.findAll().size();
        e.setDeleted(true);
        ExpenseDTO eDTO = new ExpenseDTO(e);
        HttpEntity<ExpenseDTO> httpEntity = new HttpEntity<>(eDTO, headers);
        ResponseEntity<ExpenseDTO> responseEntity = restTemplate.exchange("/expenses/updateExpense", HttpMethod.PUT, httpEntity, ExpenseDTO.class );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(current_size-1, expenseService.findAll().size());

    }

    @Test
    public void UpdateExpense_InvalidId_ReturnsNotFound(){
        ExpenseDTO eDTO = new ExpenseDTO();
        eDTO.setId(15);
        HttpEntity<ExpenseDTO> httpEntity = new HttpEntity<>(eDTO, headers);
        ResponseEntity<ExpenseDTO> responseEntity = restTemplate.exchange("/expenses/updateExpense", HttpMethod.PUT, httpEntity, ExpenseDTO.class );

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

    }

    @Test
    public void CreateExpense_ValidExpense_ReturnsOk(){

        int current_size = expenseService.findAll().size();
        Expense e = new Expense(15,"nabavka posudja", 20000, 1637193800, false);
        ExpenseDTO eDTO = new ExpenseDTO(e);
        HttpEntity<ExpenseDTO> httpEntity = new HttpEntity<>(eDTO, headers);
        ResponseEntity<ExpenseDTO> responseEntity = restTemplate.exchange("/expenses", HttpMethod.POST, httpEntity, ExpenseDTO.class );

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(current_size+1, expenseService.findAll().size());
    }

    @Test
    public void CreateExpense_InvalidExpense_ReturnNotFound(){
        Expense e = new Expense(15,"", 20000, 1637193800, false);
        ExpenseDTO eDTO = new ExpenseDTO(e);
        HttpEntity<ExpenseDTO> httpEntity = new HttpEntity<>(eDTO, headers);
        ResponseEntity<ExpenseDTO> responseEntity = restTemplate.exchange("/expenses", HttpMethod.POST, httpEntity, ExpenseDTO.class );

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void DeleteExpense_ValidId_ReturnsOk(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<Void> responseEntity = restTemplate.exchange("/expenses/1", HttpMethod.DELETE, httpEntity, void.class );

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNull(expenseService.findOne(1));

    }

    @Test
    public void DeleteExpense_InvalidId_ReturnsOk(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<Void> responseEntity = restTemplate.exchange("/expenses/15", HttpMethod.DELETE, httpEntity, void.class );

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

    }
}
