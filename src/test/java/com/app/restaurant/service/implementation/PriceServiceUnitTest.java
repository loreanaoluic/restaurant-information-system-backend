package com.app.restaurant.service.implementation;

import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.MenuItem;
import com.app.restaurant.model.Price;
import com.app.restaurant.repository.PriceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("classpath:application-test.properties")
public class PriceServiceUnitTest {
    @Autowired
    PriceService priceService;

    @MockBean
    PriceRepository priceRepository;

    @Before
    public void setUp(){
        Price price = new Price(1000, System.currentTimeMillis(), new MenuItem());
        price.setId(1);

        when(this.priceRepository.save(Mockito.any(Price.class))).thenAnswer(i -> i.getArguments()[0]);
        when(this.priceRepository.findById(1)).thenReturn(java.util.Optional.of(price));
    }

    @Test
    public void update_existingPrice_returnsUpdated() throws Exception {
        Price price = this.priceRepository.findById(1).get();

        assertDoesNotThrow(() -> this.priceService.update(price, 1));
        assertEquals(price, this.priceService.update(price, 1));
    }


    @Test
    public void update_nonExistingPrice_throwsNotFound(){
        Price price = this.priceRepository.findById(1).get();

        NotFoundException nfe = assertThrows(NotFoundException.class, () -> this.priceService.update(price, 1111));
        assertEquals("Price with given id does not exist.", nfe.getMessage());
    }
}
