package com.app.restaurant.controller;

import com.app.restaurant.dto.ExpenseDTO;
import com.app.restaurant.dto.UserDTO;
import com.app.restaurant.dto.UserTokenState;
import com.app.restaurant.model.users.User;
import com.app.restaurant.security.auth.JwtAuthenticationRequest;
import com.app.restaurant.service.implementation.ExpenseService;
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
public class DirectorControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    ExpenseService expenseService;

    private HttpHeaders headers;

    @BeforeEach
    public void login() {

        ResponseEntity<UserTokenState> responseEntity =
                restTemplate.postForEntity("/api/auth/login",
                        new JwtAuthenticationRequest("nemanja", "1234"),
                        UserTokenState.class);

        String accessToken = Objects.requireNonNull(responseEntity.getBody()).getAccessToken();
        headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

    }

    @Test
    public void GetAll_ReturnsOk(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<UserDTO[]> responseEntity = restTemplate.exchange("/api/director/getAll", HttpMethod.GET, httpEntity, UserDTO[].class );

        UserDTO[] users = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Nemanja", users[0].getName());
        assertEquals("Milutinovic", users[0].getLastName());
    }
}
