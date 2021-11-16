package com.app.restaurant.service;

import com.app.restaurant.model.DrinkCardItem;
import com.app.restaurant.model.MenuItem;
import com.app.restaurant.model.users.Manager;

public interface IManagerService extends IGenericService<Manager> {

    MenuItem createNewMenuItem(MenuItem menuItem);

    DrinkCardItem createNewDrinkCardItem(DrinkCardItem drinkCardItem);
}
