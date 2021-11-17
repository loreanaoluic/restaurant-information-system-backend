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

    @Autowired
    public DrinkCardItemDTOToDrinkCardItem(IDrinkCardService drinkCardService) {
        this.drinkCardService = drinkCardService;
    }

    @Override
    public DrinkCardItem convert(DrinkCardItemDTO drinkCardItemDTO) {

        return new DrinkCardItem(drinkCardItemDTO.getId(), drinkCardItemDTO.getIngredients(), drinkCardItemDTO.getImage(),
                drinkCardItemDTO.getDescription(), new Price(), drinkCardService.findOne(drinkCardItemDTO.getId()));
    }
}
