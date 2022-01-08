package com.app.restaurant.service.implementation;

import com.app.restaurant.exception.DuplicateEntityException;
import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.DrinkCardItem;
import com.app.restaurant.model.MenuItem;
import com.app.restaurant.model.Price;
import com.app.restaurant.repository.MenuItemRepository;
import com.app.restaurant.service.IMenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService implements IMenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final PriceService priceService;

    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository, PriceService priceService) {
        this.menuItemRepository = menuItemRepository;
        this.priceService = priceService;
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
    public MenuItem save(MenuItem menuItem){

        //had to comment lines below because they broke my functionality - Mladen
//        if(menuItem.getId() != null){
//            if(this.findOne(menuItem.getId()) != null){
//                throw new DuplicateEntityException(String.format("Menuitem with id %s already exists", menuItem.getId()));
//            }
//        }

        return menuItemRepository.save(menuItem);
    }

    @Override
    public void delete(MenuItem menuItem) {
        menuItem.setDeleted(true);
        menuItemRepository.save(menuItem);
    }

    @Override
    public MenuItem update(MenuItem menuItem, Integer id) throws Exception {
        MenuItem updated = this.findOne(id);

        if (updated == null) {
            throw new NotFoundException("Menu item with given id does not exist.");
        }

        updated.setIngredients(menuItem.getIngredients());
        updated.setImage(menuItem.getImage());
        updated.setDescription(menuItem.getDescription());
        updated.setName(menuItem.getName());

        if (menuItem.getPrice().getValue() != updated.getPrice().getValue()) {
            updated.setPrice(menuItem.getPrice());
        }

        menuItemRepository.save(updated);

        Price price = priceService.findOne(menuItem.getPrice().getId());

        if (price == null) {
            throw new NotFoundException("Price with given id does not exist.");
        }

        price.setItem(updated);
        priceService.save(price);

        return updated;
    }
}
