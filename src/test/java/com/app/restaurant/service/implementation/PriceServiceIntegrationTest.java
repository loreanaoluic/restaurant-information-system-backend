package com.app.restaurant.service.implementation;

import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.Item;
import com.app.restaurant.model.Menu;
import com.app.restaurant.model.MenuItem;
import com.app.restaurant.model.Price;
import com.app.restaurant.repository.PriceRepository;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("classpath:application-test.properties")
public class PriceServiceIntegrationTest {

    @Autowired
    PriceService priceService;

    @Autowired
    PriceRepository priceRepository;

    @Test
    public void findAll_successfullyFindsAll(){
        List<Price> allPrices = this.priceService.findAll();

        assertEquals(3, allPrices.size());
    }

    @Test
    public void findOne_existingId_returnsPrice(){
        Price p = this.priceService.findOne(2);

        assertNotNull(p);
        assertEquals(300, p.getValue(), 0);
    }

    @Test
    public void findOne_nonExistingId_returnsNull(){
        Price p = this.priceService.findOne(55);

        assertNull(p);
    }

    @Test
    public void save_validPrice_returnsSaved(){
        Price price = new Price();
        MenuItem mi = new MenuItem();
        mi.setId(1);
        mi.setMenu(new Menu(1, new ArrayList<>()));

        price.setValue(5555);
        price.setStartDate(System.currentTimeMillis());
        price.setItem(mi);

        Price saved = this.priceService.save(price);

        assertNotNull(price);
        assertEquals(price.getValue(), saved.getValue());
        assertEquals(price.getStartDate(), saved.getStartDate());
    }

    @Test
    public void save_missingSomeFields_returnsSaved(){
        Price price = new Price();
        MenuItem mi = new MenuItem();
        mi.setId(1);
        mi.setMenu(new Menu(1, new ArrayList<>()));

        price.setItem(mi);

        Price saved = this.priceService.save(price);

        assertNotNull(price);
        assertEquals(price.getItem().getId(), saved.getItem().getId());
    }

    @Test
    public void save_nullEntity_throwsInvalidDataAccessApiUsage(){
        InvalidDataAccessApiUsageException idaaue = assertThrows(InvalidDataAccessApiUsageException.class, ()-> this.priceService.save(null));
        assertEquals("Entity must not be null.; nested exception is java.lang.IllegalArgumentException: Entity must not be null.",
                idaaue.getMessage());
    }

    @Test
    public void Update_ExistingPrice_UpdatesPrice() throws Exception {
        Price price = this.priceRepository.findById(1).get();
        price.setValue(1234);
        Price beforeUpdate = this.priceRepository.findById(1).get();

        assertDoesNotThrow(() -> this.priceService.update(price, price.getId()));
        assertNotEquals(beforeUpdate.getValue(), this.priceService.update(price, price.getId()).getValue());
    }

    @Test
    public void Update_ExistingPriceFiveTimes_UpdatesPrice() throws Exception {
        Price price = this.priceRepository.findById(1).get();
        price.setValue(5555);
        Price beforeUpdate = this.priceRepository.findById(1).get();

        assertDoesNotThrow(() -> this.priceService.update(price, price.getId()));
        assertDoesNotThrow(() -> this.priceService.update(price, price.getId()));
        assertDoesNotThrow(() -> this.priceService.update(price, price.getId()));
        assertDoesNotThrow(() -> this.priceService.update(price, price.getId()));
        assertDoesNotThrow(() -> this.priceService.update(price, price.getId()));

        assertNotEquals(beforeUpdate.getValue(), this.priceService.update(price, price.getId()).getValue());
    }

    @Test
    public void Update_NonExistingPrice_ThrowsNotFound(){
        Price price = this.priceRepository.findById(1).get();
        price.setValue(5555);
        NotFoundException nfe = assertThrows(NotFoundException.class, () -> this.priceService.update(price, 1234));

        assertEquals("Price with given id does not exist.", nfe.getMessage());
    }
}
