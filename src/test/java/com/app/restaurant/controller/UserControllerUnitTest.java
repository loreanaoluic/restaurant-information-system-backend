package com.app.restaurant.controller;

import com.app.restaurant.model.Role;
import com.app.restaurant.model.users.Bartender;
import com.app.restaurant.model.users.User;
import com.app.restaurant.service.implementation.UserService;
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
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class UserControllerUnitTest {

    @MockBean
    private UserService userService;

    @Autowired
    private TestRestTemplate restTemplate;

   /* @Test
    @WithMockUser(roles = "MANAGER", username= "dusan",password = "$2a$10$vnbp6TE0PEATtxRoxzzGHOUfb76RxBI.O9l8WAJAA.L.aZIE6O5ry")
    public void post_updateUser() throws IOException {
        // given
        User u2= new Bartender();
        u2.setRole(new Role(6,"Bartender"));
        u2.setId(100);
        u2.setName("Milica");
        u2.setLastName("Mitrovic");
        u2.setEmailAddress("milica@gmail.com");
        u2.setUsername("milica");
        u2.setPassword("123");
        u2.setDeleted(false);
        User[] users = new User[1];
        users[0] = u2;
        given(userService.findAll())
                .willReturn(Arrays.asList(users));

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setOutputStreaming(false);
        requestFactory.createRequest(URI.create("http://localhost:8080/api/user/update-user"), HttpMethod.POST);

        RestTemplate rest=new RestTemplate(requestFactory);
        rest.

        // when
        ResponseEntity<User[]> userRes = restTemplate.postForEntity("/api/user/update-user",u2, User[].class);

        // then
        Assertions.assertEquals(HttpStatus.OK, userRes.getStatusCode());
        Assertions.assertEquals(users, userRes.getBody());

    }*/
}
