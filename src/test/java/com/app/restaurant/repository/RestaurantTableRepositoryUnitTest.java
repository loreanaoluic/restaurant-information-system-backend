package com.app.restaurant.repository;

import com.app.restaurant.model.MenuItem;
import com.app.restaurant.model.ReceiptItem;
import com.app.restaurant.model.Request;
import com.app.restaurant.model.RestaurantTable;
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
public class RestaurantTableRepositoryUnitTest {

    @Autowired
    private RestaurantTableRepository restaurantTableRepository;

    @Test
    public void FindAll_FindingAllNotDeletedRestaurantTables_ReturnsRestaurantTablesList(){
        List<RestaurantTable> restaurantTables = restaurantTableRepository.findAll();
        assertEquals(1, restaurantTables.size());
    }
}
