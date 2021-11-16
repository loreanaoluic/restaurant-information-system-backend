package com.app.restaurant.service.implementation;

import com.app.restaurant.model.DrinkCardItem;
import com.app.restaurant.model.MenuItem;
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
    private final DrinkCardItemRepository drinkCardItemRepository;
    private final MenuItemRepository menuItemRepository;

    @Autowired
    public ManagerService(ManagerRepository managerRepository, DrinkCardItemRepository drinkCardItemRepository, MenuItemRepository menuItemRepository) {
        this.managerRepository = managerRepository;
        this.drinkCardItemRepository = drinkCardItemRepository;
        this.menuItemRepository = menuItemRepository;
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
    public DrinkCardItem createNewDrinkCardItem(DrinkCardItem drinkCardItem) {
        drinkCardItemRepository.save(drinkCardItem);
        return drinkCardItem;
    }

    @Override
    public MenuItem createNewMenuItem(MenuItem menuItem) {
        menuItemRepository.save(menuItem);
        return menuItem;
    }
}
