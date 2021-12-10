package com.app.restaurant.service.implementation;

import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.DrinkCardItem;
import com.app.restaurant.model.MenuItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class MenuItemServiceIntegrationTest {
    @Autowired
    MenuItemService menuItemService;

    @Test
    public void UpdateMenuItem_ValidMenuItemId_MenuItem() throws Exception {
        MenuItem menuItem = menuItemService.findOne(1);
        menuItem.setDescription("noviOpis");
        menuItemService.update(menuItem, 1);
        assertEquals("noviOpis", menuItemService.findOne(1).getDescription());
    }

    @Test
    public void UpdateMenuItem_InvalidMenuItemId_ThrowsNotFoundException() throws Exception {
        MenuItem menuItem = menuItemService.findOne(1);
        menuItem.setDescription("noviOpis");
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            menuItemService.update(menuItem, 60);
        });
        assertEquals("Menu item with given id does not exist.", thrown.getMessage());
    }
}
