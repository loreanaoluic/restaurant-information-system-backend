package com.app.restaurant.support;

import com.app.restaurant.dto.MenuItemDTO;
import com.app.restaurant.model.Item;
import com.app.restaurant.model.MenuItem;
import com.app.restaurant.model.Price;
import com.app.restaurant.service.IMenuService;
import com.app.restaurant.service.IPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class MenuItemDTOToMenuItem implements Converter<MenuItemDTO, MenuItem> {

    private final IMenuService menuService;
    private final IPriceService priceService;

    @Autowired
    public MenuItemDTOToMenuItem(IMenuService menuService, IPriceService priceService) {
        this.menuService = menuService;
        this.priceService = priceService;
    }

    @Override
    public MenuItem convert(MenuItemDTO menuItemDTO) {

        Price price = new Price(menuItemDTO.getPrice(), System.currentTimeMillis());

        MenuItem menuItem = new MenuItem(menuItemDTO.getId(), menuItemDTO.getIngredients(), menuItemDTO.getImage(),
                menuItemDTO.getDescription(), price, menuService.findOne(menuItemDTO.getId()));

        priceService.save(price);

        return menuItem;
    }
}
