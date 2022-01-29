package com.app.restaurant.controller;


import com.app.restaurant.dto.ReceiptDTO;
import com.app.restaurant.dto.UserDTO;
import com.app.restaurant.dto.UserTokenState;
import com.app.restaurant.model.Receipt;
import com.app.restaurant.model.Role;
import com.app.restaurant.model.users.Manager;
import com.app.restaurant.model.users.User;
import com.app.restaurant.security.auth.JwtAuthenticationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

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
    public void UpdateUser_ValidUser_ReturnsOk(){

        UserDTO userDTO = new UserDTO();
        userDTO.setDtype("Manager");
        userDTO.setName("Miki");
        userDTO.setLastName("Mikic");
        userDTO.setEmailAddress("miladen@gmail.com");
        userDTO.setUsername("mladen");
        userDTO.setPassword("1234");
        userDTO.setDeleted(false);

        HttpEntity<Object> httpEntity = new HttpEntity<>(userDTO, headers);
        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/api/user/update-user", HttpMethod.POST, httpEntity, UserDTO.class);

        UserDTO user = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assert user != null;
        assertEquals("Miki", user.getName());
    }

    @Test
    public void UpdateUser_InvalidUser_ReturnsNotFound(){

        UserDTO userDTO = new UserDTO();
        userDTO.setDtype("Manager");
        userDTO.setName("Ducko");
        userDTO.setLastName("Antic");
        userDTO.setEmailAddress("dusan@gmail.com");
        userDTO.setUsername("dusan123");
        userDTO.setPassword("1234");
        userDTO.setDeleted(false);

        HttpEntity<Object> httpEntity = new HttpEntity<>(userDTO, headers);
        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/api/user/update-user", HttpMethod.POST, httpEntity, UserDTO.class);

        UserDTO user = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void CreateUser_ValidUser_ReturnsOK(){

        UserDTO userDTO = new UserDTO();
        userDTO.setDtype("Manager");
        userDTO.setName("Mirko");
        userDTO.setLastName("Zmirko");
        userDTO.setEmailAddress("mirko@gmail.com");
        userDTO.setUsername("mirko");
        userDTO.setPassword("1234");
        userDTO.setDeleted(false);

        HttpEntity<Object> httpEntity = new HttpEntity<>(userDTO, headers);
        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/api/user/new-user", HttpMethod.POST, httpEntity, UserDTO.class);

        UserDTO user = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assert user != null;
        assertEquals("Mirko", user.getName());
    }

    @Test
    public void CreateUser_invalidUser_ReturnsError(){

        UserDTO userDTO = new UserDTO();
        userDTO.setDtype("Manager");
        userDTO.setName("Mirko");
        userDTO.setLastName("Zmirko");
        userDTO.setEmailAddress("mirko@gmail.com");
        userDTO.setUsername("dusan");
        userDTO.setPassword("1234");
        userDTO.setDeleted(false);

        HttpEntity<Object> httpEntity = new HttpEntity<>(userDTO, headers);
        ResponseEntity<UserDTO> responseEntity =
                restTemplate.exchange("/api/user/new-user", HttpMethod.POST, httpEntity, UserDTO.class);

        UserDTO user = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}
