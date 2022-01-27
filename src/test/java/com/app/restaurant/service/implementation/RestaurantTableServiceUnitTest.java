package com.app.restaurant.service.implementation;

import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.*;
import com.app.restaurant.model.enums.ReceiptItemStatus;
import com.app.restaurant.model.enums.TableShape;
import com.app.restaurant.model.enums.TableStatus;
import com.app.restaurant.repository.ReceiptRepository;
import com.app.restaurant.repository.RestaurantTableRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("classpath:application-test.properties")
public class RestaurantTableServiceUnitTest {

    @Autowired
    RestaurantTableService restaurantTableService;

    @MockBean
    RestaurantTableRepository restaurantTableRepository;

    @BeforeEach
    public void setup(){
        List<RestaurantTable> restaurantTables = new ArrayList<>();
        RestaurantTable restaurantTable = new RestaurantTable(1, TableStatus.NOT_OCCUPIED, TableShape.ROUND,
                0.0, 0.0, false, new Receipt());
        restaurantTables.add(restaurantTable);

        given(restaurantTableRepository.findAll()).willReturn(restaurantTables);
        given(restaurantTableRepository.findById(1)).willReturn(java.util.Optional.of(restaurantTable));
        given(restaurantTableRepository.save(restaurantTable)).willReturn(restaurantTable);
    }

    @Test
    public void DeleteRestaurantTable_ValidRestaurantTableId_ReturnsVoid() {
        assertDoesNotThrow(() -> restaurantTableService.delete(1));
    }

    @Test
    public void DeleteRestaurantTable_InvalidRestaurantTableId_ThrowsNotFoundException() {
        assertThrows(NotFoundException.class, () ->restaurantTableService.delete(100));
    }


    @Test
    public void UpdateRestaurantTable_ValidRestaurantTable_ReturnsRestaurantTable() throws Exception {
        RestaurantTable restaurantTable = new RestaurantTable(1, TableStatus.OCCUPIED, TableShape.ROUND,
                0.0, 0.0, false, new Receipt());
        RestaurantTable updated = restaurantTableService.update(restaurantTable);

        assertEquals(1, updated.getId());
        assertEquals(TableStatus.OCCUPIED, updated.getTableStatus());
    }

    @Test
    public void UpdateRestaurantTable_InvalidRestaurantTable_ThrowsNotFoundException() {
        RestaurantTable restaurantTable = new RestaurantTable(100, TableStatus.OCCUPIED, TableShape.ROUND,
                0.0, 0.0, false, new Receipt());
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            restaurantTableService.update(restaurantTable);
        });
        assertEquals("Restaurant table with given id does not exist.", thrown.getMessage());
    }
}
