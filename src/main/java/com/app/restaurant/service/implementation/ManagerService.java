package com.app.restaurant.service.implementation;

import com.app.restaurant.model.DrinkCardItem;
import com.app.restaurant.model.MenuItem;
import com.app.restaurant.model.Price;
import com.app.restaurant.model.users.Manager;
import com.app.restaurant.repository.DrinkCardItemRepository;
import com.app.restaurant.repository.ManagerRepository;
import com.app.restaurant.repository.MenuItemRepository;
import com.app.restaurant.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerService implements IManagerService {

    private final ManagerRepository managerRepository;

    private final MenuItemService menuItemService;
    private final DrinkCardItemService drinkCardItemService;

    @Autowired
    public ManagerService(ManagerRepository managerRepository, MenuItemService menuItemService, DrinkCardItemService drinkCardItemService) {
        this.managerRepository = managerRepository;
        this.drinkCardItemService = drinkCardItemService;
        this.menuItemService = menuItemService;
    }

    @Override
    public List<Manager> findAll() {
        return managerRepository.findAll();
    }

    @Override
    public Manager findOne(Integer id) {
        return managerRepository.findById(id).orElse(null);
    }

    @Override
    public Manager save(Manager manager) {
        return managerRepository.save(manager);
    }

    @Override
    public DrinkCardItem createNewDrinkCardItem(DrinkCardItem drinkCardItem, double price) {
        Price newPrice = drinkCardItem.getPrice();
        newPrice.setValue(price);
        newPrice.setStartDate(System.currentTimeMillis());
        drinkCardItemService.save(drinkCardItem);

        return drinkCardItem;
    }

    @Override
    public MenuItem createNewMenuItem(MenuItem menuItem, double price) {
        Price newPrice = menuItem.getPrice();
        newPrice.setValue(price);
        newPrice.setStartDate(System.currentTimeMillis());
        menuItemService.save(menuItem);

        return menuItem;
    }

    @Override
    public MenuItem updateMenuItem(MenuItem menuItem, double price) {
        Price newPrice = menuItem.getPrice();
        newPrice.setValue(price);
        newPrice.setStartDate(System.currentTimeMillis());

        menuItemService.update(menuItem, menuItem.getId());

        return menuItem;
    }

    @Override
    public DrinkCardItem updateDrinkCardItem(DrinkCardItem drinkCardItem, double price) {
        Price newPrice = drinkCardItem.getPrice();
        newPrice.setValue(price);
        newPrice.setStartDate(System.currentTimeMillis());

        drinkCardItemService.update(drinkCardItem, drinkCardItem.getId());

        return drinkCardItem;
    }
}
