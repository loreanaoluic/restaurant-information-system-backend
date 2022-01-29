package com.app.restaurant.controller;

import com.app.restaurant.dto.UserTokenState;
import com.app.restaurant.exception.InvalidValueException;
import com.app.restaurant.model.Request;
import com.app.restaurant.model.users.Chef;
import com.app.restaurant.security.auth.JwtAuthenticationRequest;
import com.app.restaurant.service.IRequestService;
import org.junit.jupiter.api.Assertions;
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
public class ChefControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private IRequestService requestService;

    private HttpHeaders headers;

    @BeforeEach
    public void login() {

        ResponseEntity<UserTokenState> responseEntity =
                restTemplate.postForEntity("/api/auth/login",
                        new JwtAuthenticationRequest("loreana", "1234"),
                        UserTokenState.class);

        String accessToken = Objects.requireNonNull(responseEntity.getBody()).getAccessToken();
        headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void createRequest_ValidExpense_ReturnsOk(){
        int current_size = requestService.findAll().size();
        Request request = new Request(3,370.0, "ingredients", "description", "../../../../assets/images/choco-cake.jpg", "Chocolate cake", 5.0, false, new Chef());

        HttpEntity<Request> httpEntity = new HttpEntity<>(request, headers);
        ResponseEntity<Request> responseEntity = restTemplate.exchange("/api/chef/new-request", HttpMethod.POST, httpEntity, Request.class );

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(current_size+1, requestService.findAll().size());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void createRequest_InvalidExpense_ReturnsInternalServerError(){
        int current_size = requestService.findAll().size();
        Request request = new Request(3,370.0, "ingredients", "description", "../../../../assets/images/choco-cake.jpg", "", 5.0, false, new Chef());

        HttpEntity<Request> httpEntity = new HttpEntity<>(request, headers);

        ResponseEntity<InvalidValueException> thrown = restTemplate.exchange("/api/chef/new-request", HttpMethod.POST, httpEntity, InvalidValueException.class );
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, thrown.getStatusCode());
    }
}
