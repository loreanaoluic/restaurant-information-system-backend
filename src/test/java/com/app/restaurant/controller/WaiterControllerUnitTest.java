package com.app.restaurant.controller;

import com.app.restaurant.model.Menu;
import com.app.restaurant.model.MenuItem;
import com.app.restaurant.model.Price;
import com.app.restaurant.model.Receipt;
import com.app.restaurant.model.users.Waiter;
import com.app.restaurant.service.implementation.MenuItemService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.junit.jupiter.api.Assertions;

import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class WaiterControllerUnitTest {

    @MockBean
    private MenuItemService menuItemService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @WithMockUser(roles = "WAITER")
    public void GetMenuItems_ReturnsMenuItems() {
//        // given
        
//        MenuItem menuItem = new MenuItem(1, "piletina", "sastojci", "slika", "opis", new Price(1), new Menu(), 1);
//        MenuItem[] menuItems = new MenuItem[1];
//        menuItems[0] = menuItem;
//        given(menuItemService.findAll())
//                .willReturn(Arrays.asList(menuItems));
//
//        // when
//        ResponseEntity<MenuItem[]> menuItemResponse = restTemplate.getForEntity("/api/waiter/all-menu-items", MenuItem[].class);
//
//        // then
//        Assertions.assertEquals(HttpStatus.OK, menuItemResponse.getStatusCode());
//        Assertions.assertEquals(menuItems, menuItemResponse.getBody());
    }
}
