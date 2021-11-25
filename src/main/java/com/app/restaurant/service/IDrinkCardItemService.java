package com.app.restaurant.service;

import com.app.restaurant.model.DrinkCardItem;

public interface IDrinkCardItemService extends IGenericService<DrinkCardItem> {

    DrinkCardItem update(DrinkCardItem drinkCardItem, Integer id) throws Exception;

}
