package com.app.restaurant.service.implementation;

import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.Expense;
import com.app.restaurant.model.Menu;
import com.app.restaurant.model.MenuItem;
import com.app.restaurant.model.Price;
import com.app.restaurant.repository.ExpenseRepository;
import com.app.restaurant.repository.MenuItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MenuItemServiceUnitTest {
    @Autowired
    private MenuItemService menuItemService;

    @MockBean
    private MenuItemRepository menuItemRepository;

    @BeforeEach
    public void setUp() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(1,"piletina", "sastojci", "slika", "opis", new Price(1), new Menu(), 1));

        given(menuItemRepository.findAll()).willReturn(menuItems);

        MenuItem menuItem = new MenuItem(1,"piletina", "sastojci", "slika", "opis", new Price(1), new Menu(), 1);
        MenuItem savedMenuItem = new MenuItem(1,"piletina", "sastojci", "slika", "opis", new Price(1), new Menu(), 1);

        given(menuItemRepository.findById(1))
                .willReturn(java.util.Optional.of(menuItem));

        given(menuItemRepository.save(menuItem)).willReturn(savedMenuItem);

    }

    @Test
    public void UpdateMenuItem_ValidMenuId_ReturnsMenuItem() throws Exception {
        MenuItem menuItem = new MenuItem(1,"piletina", "sastojci", "slika", "opis",
                new Price(1), new Menu(), 1);
        MenuItem created = menuItemService.update(menuItem, 1);

        assertEquals("piletina", created.getName());
    }

    @Test
    public void UpdateMenuItem_InvalidMenuId_ThrowsNotFoundException() throws Exception {
        MenuItem menuItem = new MenuItem(1,"piletina", "sastojci", "slika", "opis",
                new Price(1), new Menu(), 1);

        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            menuItemService.update(menuItem, 100);
        });
        assertEquals("Menu item with given id does not exist.", thrown.getMessage());
    }

    @Test
    public void UpdateMenuItem_InvalidPriceId_ThrowsNotFoundException() throws Exception {
        MenuItem menuItem = new MenuItem(1,"piletina", "sastojci", "slika", "opis",
                new Price(3), new Menu(), 1);

        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            menuItemService.update(menuItem, 1);
        });
        assertEquals("Price with given id does not exist.", thrown.getMessage());
    }
}
