package com.app.restaurant.service.implementation;

import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.DrinkCardItem;
import com.app.restaurant.model.Item;
import com.app.restaurant.model.Receipt;
import com.app.restaurant.model.ReceiptItem;
import com.app.restaurant.model.enums.ReceiptItemStatus;
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
public class DrinkCardItemServiceIntegrationTest {

    @Autowired
    DrinkCardItemService drinkCardItemService;

    @Test
    public void UpdateDrinkCardItem_ValidDrinkCardItemId_DrinkCardItem() throws Exception {
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
