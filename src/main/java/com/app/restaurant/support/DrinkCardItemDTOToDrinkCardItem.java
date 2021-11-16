package com.app.restaurant.support;

import com.app.restaurant.dto.DrinkCardItemDTO;
import com.app.restaurant.model.DrinkCardItem;
import com.app.restaurant.model.Price;
import com.app.restaurant.service.IDrinkCardService;
import com.app.restaurant.service.IPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DrinkCardItemDTOToDrinkCardItem implements Converter<DrinkCardItemDTO, DrinkCardItem> {

    private final IDrinkCardService drinkCardService;
    private final IPriceService priceService;

    @Autowired
    public DrinkCardItemDTOToDrinkCardItem(IDrinkCardService drinkCardService, IPriceService priceService) {
        this.drinkCardService = drinkCardService;
        this.priceService = priceService;
    }

    @Override
    public DrinkCardItem convert(DrinkCardItemDTO drinkCardItemDTO) {
        Price price = new Price(drinkCardItemDTO.getPrice(), System.currentTimeMillis());

        DrinkCardItem drinkCardItem = new DrinkCardItem(drinkCardItemDTO.getId(), drinkCardItemDTO.getIngredients(), drinkCardItemDTO.getImage(),
                drinkCardItemDTO.getDescription(), price, drinkCardService.findOne(drinkCardItemDTO.getId()));

        priceService.save(price);

        return drinkCardItem;
    }
}
