package com.app.restaurant.service.implementation;

import com.app.restaurant.model.DrinkCardItem;
import com.app.restaurant.model.MenuItem;
import com.app.restaurant.model.Price;
import com.app.restaurant.repository.MenuItemRepository;
import com.app.restaurant.service.IMenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService implements IMenuItemService {

    private final MenuItemRepository menuItemRepository;

    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public List<MenuItem> findAll() {
        return menuItemRepository.findAll();
    }

    @Override
    public MenuItem findOne(Integer id) {
        return menuItemRepository.findById(id).orElse(null);
    }

    @Override
    public MenuItem save(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    @Override
    public MenuItem update(MenuItem menuItem, Integer id) {
        MenuItem updated = this.findOne(id);

        if (updated == null) {
            return null;
        }

        updated.setIngredients(menuItem.getIngredients());
        updated.setImage(menuItem.getImage());
        updated.setDescription(menuItem.getDescription());

        menuItemRepository.save(updated);
        return updated;
    }
}
