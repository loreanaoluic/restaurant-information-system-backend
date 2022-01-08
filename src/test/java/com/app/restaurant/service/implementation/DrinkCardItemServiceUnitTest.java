package com.app.restaurant.service.implementation;

import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.DrinkCard;
import com.app.restaurant.model.DrinkCardItem;
import com.app.restaurant.model.Price;
import com.app.restaurant.repository.DrinkCardItemRepository;
import com.app.restaurant.repository.PriceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class DrinkCardItemServiceUnitTest {

    @Autowired
    private DrinkCardItemService drinkCardItemService;

    @MockBean
    private DrinkCardItemRepository drinkCardItemRepository;

    @MockBean
    private PriceRepository priceRepository;

    @BeforeEach
    public void setup() {
        List<DrinkCardItem> drinkCardItems = new ArrayList<>();
        drinkCardItems.add(new DrinkCardItem(100, "naziv", "sastojci", "slika", "opis",
                false, new Price(1), new DrinkCard()));

        given(drinkCardItemRepository.findAll()).willReturn(drinkCardItems);

        DrinkCardItem drinkCardItem = new DrinkCardItem(100, "naziv", "sastojci", "slika",
                "opis", false, new Price(1), new DrinkCard());
        DrinkCardItem savedDrinkCardItem = new DrinkCardItem(100, "naziv", "sastojci", "slika",
                "opis", false, new Price(1), new DrinkCard());

        given(drinkCardItemRepository.findById(100))
                .willReturn(java.util.Optional.of(savedDrinkCardItem));

        given(drinkCardItemRepository.save(drinkCardItem)).willReturn(savedDrinkCardItem);

        Price price = new Price(1);
        Price savedPrice = new Price(1);

        given(priceRepository.findById(1))
                .willReturn(java.util.Optional.of(savedPrice));

        given(priceRepository.save(price)).willReturn(savedPrice);

    }

    @Test
    public void UpdateDrinkCardItem_ValidDrinkCardItemId_ReturnsDrinkCardItem() throws Exception {
        DrinkCardItem drinkCardItem = new DrinkCardItem(100, "naziv", "sastojci", "slika",
                "opis", false, new Price(1), new DrinkCard());
        DrinkCardItem created = drinkCardItemService.update(drinkCardItem, 100);

        assertEquals("naziv", created.getName());
    }

    @Test
    public void UpdateDrinkCardItem_InvalidDrinkCardItemId_ThrowsNotFoundException() throws Exception {
        DrinkCardItem drinkCardItem = new DrinkCardItem(100, "naziv", "sastojci", "slika",
                "opis", false, new Price(1), new DrinkCard());

        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            drinkCardItemService.update(drinkCardItem, 60);
        });
        assertEquals("Drink card item with given id does not exist.", thrown.getMessage());
    }

    @Test
    public void UpdateDrinkCardItem_InvalidPriceId_ThrowsNotFoundException() throws Exception {
        DrinkCardItem drinkCardItem = new DrinkCardItem(100, "naziv", "sastojci", "slika",
                "opis", false, new Price(3), new DrinkCard());

        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            drinkCardItemService.update(drinkCardItem, 100);
        });
        assertEquals("Price with given id does not exist.", thrown.getMessage());
    }
}