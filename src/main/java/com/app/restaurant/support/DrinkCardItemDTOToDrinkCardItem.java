package com.app.restaurant.support;

import com.app.restaurant.dto.DrinkCardItemDTO;
import com.app.restaurant.model.DrinkCardItem;
import com.app.restaurant.model.Price;
import com.app.restaurant.service.IDrinkCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DrinkCardItemDTOToDrinkCardItem implements Converter<DrinkCardItemDTO, DrinkCardItem> {

    private final IDrinkCardService drinkCardService;
    private final PriceDTOToPrice priceDTOToPrice;

    @Autowired
    public DrinkCardItemDTOToDrinkCardItem(IDrinkCardService drinkCardService, PriceDTOToPrice priceDTOToPrice) {
        this.drinkCardService = drinkCardService;
        this.priceDTOToPrice = priceDTOToPrice;
    }

    @Override
    public DrinkCardItem convert(DrinkCardItemDTO drinkCardItemDTO) {

        if (drinkCardItemDTO.getPrice().getId() == null) {
            return new DrinkCardItem(drinkCardItemDTO.getId(), drinkCardItemDTO.getName(), drinkCardItemDTO.getIngredients(),
                    drinkCardItemDTO.getImage(), drinkCardItemDTO.getDescription(), new Price(),
                    drinkCardService.findOne(drinkCardItemDTO.getId()));
        }
        return new DrinkCardItem(drinkCardItemDTO.getId(), drinkCardItemDTO.getName(), drinkCardItemDTO.getIngredients(),
                drinkCardItemDTO.getImage(), drinkCardItemDTO.getDescription(), priceDTOToPrice.convert(drinkCardItemDTO.getPrice()),
                drinkCardService.findOne(drinkCardItemDTO.getId()));
    }
}
