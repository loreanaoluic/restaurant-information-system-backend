package com.app.restaurant.controller;

import com.app.restaurant.dto.ExpenseDTO;
import com.app.restaurant.dto.UserTokenState;
import com.app.restaurant.dto.WaiterDTO;
import com.app.restaurant.model.DrinkCardItem;
import com.app.restaurant.model.Expense;
import com.app.restaurant.model.MenuItem;
import com.app.restaurant.security.auth.JwtAuthenticationRequest;
import com.app.restaurant.service.*;
import com.app.restaurant.service.implementation.ExpenseService;
import com.app.restaurant.support.DrinkCardItemDTOToDrinkCardItem;
import com.app.restaurant.support.MenuItemDTOToMenuItem;
import com.app.restaurant.support.ReceiptItemDTOToReceiptItem;
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
public class WaiterControllerIntegrationalTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    ExpenseService expenseService;
    @Autowired
    private IMenuItemService menuItemService;
    @Autowired
    private IDrinkCardItemService drinkCardItemService;
    @Autowired
    private IWaiterService waiterService;
    @Autowired
    private IReceiptItemService receiptItemService;
    @Autowired
    private IReceiptService receiptService;
    @Autowired
    private DrinkCardItemDTOToDrinkCardItem drinkCardItemDTOToDrinkCardItem;
    @Autowired
    private MenuItemDTOToMenuItem menuItemDTOToMenuItem;
    @Autowired
    private ReceiptItemDTOToReceiptItem receiptItemDTOToReceiptItem;

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
    public void GetMenuItems_ReturnsOk(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<MenuItem[]> responseEntity = restTemplate.exchange("/api/waiter/all-menu-items", HttpMethod.GET, httpEntity, MenuItem[].class );

        MenuItem[] menuItems = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1,menuItems.length);
    }

    @Test
    public void GetDrinkCardItems_ReturnsOk(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<DrinkCardItem[]> responseEntity = restTemplate.exchange("/api/waiter/all-drink-card-items", HttpMethod.GET, httpEntity, DrinkCardItem[].class );

        DrinkCardItem[] menuItems = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1,menuItems.length);
    }


}
