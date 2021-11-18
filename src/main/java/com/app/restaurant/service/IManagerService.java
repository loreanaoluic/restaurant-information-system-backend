package com.app.restaurant.service;

import com.app.restaurant.model.DrinkCardItem;
import com.app.restaurant.model.MenuItem;
import com.app.restaurant.model.users.Manager;
import com.app.restaurant.model.users.User;

import java.util.List;

public interface IManagerService extends IGenericService<Manager> {

    MenuItem createNewMenuItem(MenuItem menuItem, double price);

    DrinkCardItem createNewDrinkCardItem(DrinkCardItem drinkCardItem, double price);

    MenuItem updateMenuItem(MenuItem menuItem);

    void delete(Integer id);

    Manager create(Manager entity) throws Exception;

    Manager update(Manager entity) throws Exception;

    DrinkCardItem updateDrinkCardItem(DrinkCardItem drinkCardItem);

}
