package com.app.restaurant.service.implementation;

import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.Receipt;
import com.app.restaurant.model.RestaurantTable;
import com.app.restaurant.model.enums.TableShape;
import com.app.restaurant.model.enums.TableStatus;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("classpath:application-test.properties")
public class RestaurantTableServiceIntegrationTest {

    @Autowired
    RestaurantTableService restaurantTableService;

    @Test
    public void findAll_successfullyFindsAll(){
        List<RestaurantTable> allTables = this.restaurantTableService.findAll();

        assertEquals(1, allTables.size());
    }

    @Test
    public void findOne_existingId_returnsRestaurantTable(){
        RestaurantTable rt = this.restaurantTableService.findOne(1);

        assertNotNull(rt);
        assertEquals(TableStatus.OCCUPIED, rt.getTableStatus());
        assertEquals(TableShape.ROUND,rt.getTableShape());
    }

    @Test
    public void findOne_nonExistingId_returnsNull(){
        RestaurantTable rt = this.restaurantTableService.findOne(222);

        assertNull(rt);
    }

    @Test
    public void save_validRestaurantTable_returnsSaved(){
        RestaurantTable restaurantTable = new RestaurantTable();
        restaurantTable.setTableStatus(TableStatus.NOT_OCCUPIED);
        restaurantTable.setTableShape(TableShape.SQUARE);
        restaurantTable.setCoordinateX(550);
        restaurantTable.setCoordinateY(100);
        restaurantTable.setDeleted(false);

        RestaurantTable saved = this.restaurantTableService.save(restaurantTable);

        assertNotNull(saved);
        assertEquals(restaurantTable.getTableShape(), saved.getTableShape());
        assertEquals(restaurantTable.getTableStatus(), saved.getTableStatus());
        assertEquals(restaurantTable.getCoordinateX(), saved.getCoordinateX(), 0);
    }

    @Test
    public void save_missingSomeFields_returnsSaved(){
        RestaurantTable restaurantTable = new RestaurantTable();
        restaurantTable.setTableStatus(TableStatus.NOT_OCCUPIED);
        restaurantTable.setTableShape(TableShape.SQUARE);
        restaurantTable.setCoordinateY(100);
        restaurantTable.setDeleted(false);

        RestaurantTable saved = this.restaurantTableService.save(restaurantTable);

        assertNotNull(saved);
        assertEquals(restaurantTable.getTableShape(), saved.getTableShape());
        assertEquals(restaurantTable.getTableStatus(), saved.getTableStatus());
    }

    @Test
    public void save_nullEntity_throwsInvalidDataAccessApiUsage(){
        InvalidDataAccessApiUsageException idaaaue = Assertions.assertThrows(InvalidDataAccessApiUsageException.class, ()-> this.restaurantTableService.save(null));
        assertEquals("Entity must not be null.; nested exception is java.lang.IllegalArgumentException: Entity must not be null.",
                idaaaue.getMessage());
    }

    @Test
    public void delete_existingTable_deletesTable(){
        assertDoesNotThrow(() -> this.restaurantTableService.delete(1));

        assertTrue(this.restaurantTableService.findOne(1).getDeleted());
    }

    @Test
    public void delete_nonExistingTable_throwsNotFound(){
        NotFoundException nfe = assertThrows(NotFoundException.class, ()->this.restaurantTableService.delete(11));

        assertEquals("Restaurant table with given id does not exist.", nfe.getMessage());
    }

    @Test
    public void update_validNewInfo_returnsUpdated() throws Exception {
        RestaurantTable newInfo = new RestaurantTable();
        newInfo.setTableStatus(TableStatus.NOT_OCCUPIED);
        newInfo.setTableShape(TableShape.BAR);
        newInfo.setCoordinateX(667);
        newInfo.setCoordinateY(185);
        newInfo.setDeleted(false);
        newInfo.setId(2);

        RestaurantTable oldInfo = this.restaurantTableService.findOne(2);
        RestaurantTable updated = this.restaurantTableService.update(newInfo);

        assertNotNull(updated);
        assertEquals(TableStatus.NOT_OCCUPIED, updated.getTableStatus());
        assertEquals(TableShape.BAR, updated.getTableShape());
        assertEquals(667, updated.getCoordinateX());
        assertFalse(updated.getDeleted());
    }

    @Test
    public void update_nonExistingTable_throwsNotFound(){
        RestaurantTable newInfo = new RestaurantTable();
        newInfo.setTableStatus(TableStatus.NOT_OCCUPIED);
        newInfo.setTableShape(TableShape.BAR);
        newInfo.setCoordinateX(667);
        newInfo.setCoordinateY(185);
        newInfo.setDeleted(false);
        newInfo.setId(22);

        NotFoundException nfe = assertThrows(NotFoundException.class, () -> this.restaurantTableService.update(newInfo));
        assertEquals("Restaurant table with given id does not exist.", nfe.getMessage());
    }
}
