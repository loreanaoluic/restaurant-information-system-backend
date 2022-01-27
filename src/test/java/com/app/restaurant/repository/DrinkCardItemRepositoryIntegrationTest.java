package com.app.restaurant.repository;

import com.app.restaurant.model.DrinkCardItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class DrinkCardItemRepositoryIntegrationTest {

    @Autowired
    private DrinkCardItemRepository drinkCardItemRepository;

    @Test
    public void FindAll_FindingAllNotDeletedDrinkCardItems_ReturnsDrinkCardItemsList(){
        List<DrinkCardItem> drinkCardItems = drinkCardItemRepository.findAll();
        assertEquals(1, drinkCardItems.size());
    }
}
