package com.app.restaurant.service.implementation;

import com.app.restaurant.model.DrinkCardItem;
import com.app.restaurant.model.MenuItem;
import com.app.restaurant.model.Price;
import com.app.restaurant.model.users.Manager;
import com.app.restaurant.repository.ManagerRepository;
import com.app.restaurant.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerService implements IManagerService {

    private final ManagerRepository managerRepository;

    private final MenuItemService menuItemService;
    private final DrinkCardItemService drinkCardItemService;
    private final PriceService priceService;

    @Autowired
    public ManagerService(ManagerRepository managerRepository, MenuItemService menuItemService, DrinkCardItemService drinkCardItemService, PriceService priceService) {
        this.managerRepository = managerRepository;
        this.drinkCardItemService = drinkCardItemService;
        this.menuItemService = menuItemService;
        this.priceService = priceService;
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
        DrinkCardItem saveId  = new DrinkCardItem();
        drinkCardItemService.save(saveId);

        Price newPrice = drinkCardItem.getPrice();
        newPrice.setValue(price);
        newPrice.setStartDate(System.currentTimeMillis());
        newPrice.setItem(saveId);

        drinkCardItem.setId(saveId.getId());
        drinkCardItemService.save(drinkCardItem);

        return drinkCardItem;
    }

    @Override
    public MenuItem createNewMenuItem(MenuItem menuItem, double price) {
        MenuItem saveId = new MenuItem();
        menuItemService.save(saveId);

        Price newPrice = menuItem.getPrice();
        newPrice.setValue(price);
        newPrice.setStartDate(System.currentTimeMillis());
        newPrice.setItem(saveId);

        menuItem.setId(saveId.getId());
        menuItemService.save(menuItem);

        return menuItem;
    }

    @Override
    public DrinkCardItem updateDrinkCardItem(DrinkCardItem drinkCardItem) {
        Price oldPrice = priceService.findOne(drinkCardItem.getPrice().getId());

        if (oldPrice.getValue() != drinkCardItem.getPrice().getValue()) {

            oldPrice.setEndDate(System.currentTimeMillis());
            priceService.save(oldPrice);

            Price newPrice = new Price();
            newPrice.setValue(drinkCardItem.getPrice().getValue());
            newPrice.setStartDate(System.currentTimeMillis());
            priceService.save(newPrice);
            drinkCardItem.setPrice(newPrice);
        }

        drinkCardItemService.update(drinkCardItem, drinkCardItem.getId());

        return drinkCardItem;
    }

    @Override
    public MenuItem updateMenuItem(MenuItem menuItem) {
        Price oldPrice = priceService.findOne(menuItem.getPrice().getId());

        if (oldPrice.getValue() != menuItem.getPrice().getValue()) {

            oldPrice.setEndDate(System.currentTimeMillis());
            priceService.save(oldPrice);

            Price newPrice = new Price();
            newPrice.setValue(menuItem.getPrice().getValue());
            newPrice.setStartDate(System.currentTimeMillis());
            priceService.save(newPrice);
            menuItem.setPrice(newPrice);
        }

        menuItemService.update(menuItem, menuItem.getId());

        return menuItem;
    }
}
