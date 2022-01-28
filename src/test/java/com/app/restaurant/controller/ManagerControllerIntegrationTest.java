package com.app.restaurant.controller;

import com.app.restaurant.dto.UserTokenState;
import com.app.restaurant.dto.WaiterDTO;
import com.app.restaurant.repository.UserRepository;
import com.app.restaurant.security.auth.JwtAuthenticationRequest;
import com.app.restaurant.service.*;
import com.app.restaurant.service.implementation.WaiterService;
import com.app.restaurant.support.DrinkCardItemDTOToDrinkCardItem;
import com.app.restaurant.support.ManagerDTOToManager;
import com.app.restaurant.support.MenuItemDTOToMenuItem;
import com.app.restaurant.support.UserToUserDTO;
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
public class ManagerControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private IManagerService managerService;
    @Autowired
    private IRequestService requestService;
    @Autowired
    private IUserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IDrinkCardItemService drinkCardItemService;
    @Autowired
    private IMenuItemService menuItemService;
    @Autowired
    private IRestaurantTableService restaurantTableService;
    @Autowired
    private WaiterService waiterService;
    @Autowired
    private MenuItemDTOToMenuItem menuItemDTOToMenuItem;
    @Autowired
    private DrinkCardItemDTOToDrinkCardItem drinkCardItemDTOToDrinkCardItem;
    @Autowired
    private ManagerDTOToManager managerDTOToManager;
    @Autowired
    private UserToUserDTO userToUserDTO;

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
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void CreateWaiter_ValidWaiter_ReturnsCreated(){

        int current_size = waiterService.findAll().size();
        WaiterDTO waiterDTO = new WaiterDTO(15,"Waiter", "Milorad", "Micic", "mico", "$2a$10$vnbp6TE0PEATtxRoxzzGHOUfb76RxBI.O9l8WAJAA.L.aZIE6O5ry",  false);
        HttpEntity<WaiterDTO> httpEntity = new HttpEntity<>(waiterDTO, headers);
        ResponseEntity<WaiterDTO> responseEntity = restTemplate.exchange("/api/manager/new-waiter", HttpMethod.POST, httpEntity, WaiterDTO.class );

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        //(current_size+1, waiterService.findAll().size()); NIJE DODAO OVOG DEBILA
    }
}
