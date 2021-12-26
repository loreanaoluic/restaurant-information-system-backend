package com.app.restaurant.service.implementation;

import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.Price;
import com.app.restaurant.repository.PriceRepository;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

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
