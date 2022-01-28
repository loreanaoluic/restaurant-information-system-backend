package com.app.restaurant.controller;

import com.app.restaurant.dto.UserTokenState;
import com.app.restaurant.model.Request;
import com.app.restaurant.model.users.Bartender;
import com.app.restaurant.security.auth.JwtAuthenticationRequest;
import com.app.restaurant.service.IRequestService;
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
public class HeadBartenderControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private IRequestService requestService;

    private HttpHeaders headers;

    @BeforeEach
    public void login() {

        ResponseEntity<UserTokenState> responseEntity =
                restTemplate.postForEntity("/api/auth/login",
                        new JwtAuthenticationRequest("nikola", "1234"),
                        UserTokenState.class);

        String accessToken = Objects.requireNonNull(responseEntity.getBody()).getAccessToken();
        headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void createRequest_ValidExpense_ReturnsOk(){
        int current_size = requestService.findAll().size();
        Request request = new Request(4,270.0, "ingredients", "description", "../../../../assets/images/latte.jpg", "Latte",  5, false, new Bartender());

        HttpEntity<Request> httpEntity = new HttpEntity<>(request, headers);
        ResponseEntity<Request> responseEntity = restTemplate.exchange("/api/head-bartender/new-request", HttpMethod.POST, httpEntity, Request.class );

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(current_size+1, requestService.findAll().size());
    }
}
