package com.app.restaurant.service.implementation;

import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.DrinkCardItem;
import com.app.restaurant.model.Menu;
import com.app.restaurant.model.MenuItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class MenuItemServiceIntegrationTest {
    @Autowired
    MenuItemService menuItemService;

    @Test
    public void findAll_successfullyFindsAll(){
        List<MenuItem> allMItems = this.menuItemService.findAll();

        assertEquals(1, allMItems.size());
    }

    @Test
    public void findOne_existingId_returnsMItem(){
        MenuItem mi = this.menuItemService.findOne(1);

        assertNotNull(mi);
        assertEquals("Pizza",mi.getName());
        assertEquals("noviOpis",mi.getDescription());
        assertEquals(1,mi.getMenu().getId());
    }

    @Test
    public void findOne_nonExistingId_returnsNull(){
        MenuItem mi = this.menuItemService.findOne(11);

        assertNull(mi);
    }

    @Test
    public void save_validMenuItem_returnsSaved(){
        MenuItem newMi = new MenuItem();
        newMi.setMenu(new Menu(1, new ArrayList<>()));
        newMi.setName("Palacinka");
        newMi.setDescription("Slatka palacinka");
        newMi.setDeleted(false);
        newMi.setIngredients("Cizkejk,oreo");

        MenuItem saved = this.menuItemService.save(newMi);

        assertEquals(newMi.getName(), saved.getName());
        assertEquals(newMi.getDescription(), saved.getDescription());
        assertEquals(newMi.getIngredients(), saved.getIngredients());
    }

    @Test
    public void save_missingSomeFields_returnsSaved(){
        MenuItem newMi = new MenuItem();
        newMi.setMenu(new Menu(1, new ArrayList<>()));
        newMi.setDeleted(false);
        newMi.setIngredients("Cizkejk,oreo");

        MenuItem saved = this.menuItemService.save(newMi);

        assertEquals(newMi.getIngredients(), saved.getIngredients());
    }

    @Test
    public void save_nullEntity_throwsInvalidDataAccessApiUsage(){
        InvalidDataAccessApiUsageException idaaaue = Assertions.assertThrows(InvalidDataAccessApiUsageException.class, ()-> this.menuItemService.save(null));
        assertEquals("Entity must not be null.; nested exception is java.lang.IllegalArgumentException: Entity must not be null.",
                idaaaue.getMessage());
    }

    @Test
    public void UpdateMenuItem_ValidMenuItemId_ReturnsMenuItem() throws Exception {
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
