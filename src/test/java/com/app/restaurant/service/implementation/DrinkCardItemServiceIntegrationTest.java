package com.app.restaurant.service.implementation;

import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.*;
import com.app.restaurant.model.enums.ReceiptItemStatus;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class DrinkCardItemServiceIntegrationTest {

    @Autowired
    DrinkCardItemService drinkCardItemService;

    @Test
    public void findAll_successfullyFindsAll(){
        List<DrinkCardItem> allDItems = this.drinkCardItemService.findAll();

        assertEquals(2,allDItems.size());
    }

    @Test
    public void findOne_existingId_returnsDItem(){
        DrinkCardItem dci = this.drinkCardItemService.findOne(2);

        assertNotNull(dci);
        assertEquals("Coca Cola", dci.getName());
        assertEquals(300, dci.getPrice().getValue());
    }

    @Test
    public void findOne_nonExistingId_returnsNull(){
        DrinkCardItem dci = this.drinkCardItemService.findOne(1);
        assertNull(dci);
    }

    @Test
    public void save_validDrinkCardItem_returnsSaved(){
        DrinkCardItem dci = new DrinkCardItem();
        dci.setName("Kokta");
        dci.setIngredients("Sipak");
        dci.setDescription("0.33l kokte");
        dci.setDrinkCard(new DrinkCard(1, new ArrayList<>()));
        dci.setDeleted(false);

        DrinkCardItem saved = this.drinkCardItemService.save(dci);

        assertEquals(dci.getName(), saved.getName());
        assertEquals(dci.getIngredients(), saved.getIngredients());
        assertEquals(dci.getDescription(), saved.getDescription());
    }

    @Test
    public void save_missingSomeFields_returnsSaved(){
        DrinkCardItem dci = new DrinkCardItem();
        dci.setIngredients("Sipak");
        dci.setDescription("0.33l kokte");
        dci.setDeleted(false);

        DrinkCardItem saved = this.drinkCardItemService.save(dci);

        assertEquals(dci.getIngredients(), saved.getIngredients());
        assertEquals(dci.getDescription(), saved.getDescription());
    }

    @Test
    public void save_nullEntity_throwsInvalidDataAccessApiUsage(){
        InvalidDataAccessApiUsageException idaaue = assertThrows(InvalidDataAccessApiUsageException.class, ()-> this.drinkCardItemService.save(null));
        assertEquals("Entity must not be null.; nested exception is java.lang.IllegalArgumentException: Entity must not be null.",
                idaaue.getMessage());
    }

    @Test
    public void UpdateDrinkCardItem_ValidDrinkCardItemId_ReturnsDrinkCardItem() throws Exception {
        DrinkCardItem drinkCardItem = drinkCardItemService.findOne(2);
        drinkCardItem.setDescription("noviOpis");
        drinkCardItemService.update(drinkCardItem, 2);
        assertEquals("noviOpis", drinkCardItemService.findOne(2).getDescription());
    }

    @Test
    public void UpdateDrinkCardItem_InvalidDrinkCardItemId_ThrowsNotFoundException() throws Exception {
        DrinkCardItem drinkCardItem = drinkCardItemService.findOne(2);
        drinkCardItem.setDescription("noviOpis");
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            drinkCardItemService.update(drinkCardItem, 60);
        });
        assertEquals("Drink card item with given id does not exist.", thrown.getMessage());
    }
}
