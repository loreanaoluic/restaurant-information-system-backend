package com.app.restaurant.support;

import com.app.restaurant.dto.MenuItemDTO;
import com.app.restaurant.model.MenuItem;
import com.app.restaurant.model.Price;
import com.app.restaurant.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;


@Component
public class MenuItemDTOToMenuItem implements Converter<MenuItemDTO, MenuItem> {

    private final IMenuService menuService;
    private final PriceDTOToPrice priceDTOToPrice;

    @Autowired
    public MenuItemDTOToMenuItem(IMenuService menuService, PriceDTOToPrice priceDTOToPrice) {
        this.menuService = menuService;
        this.priceDTOToPrice = priceDTOToPrice;
    }

    @Override
    public MenuItem convert(MenuItemDTO menuItemDTO) {
        if (menuItemDTO.getPrice().getId() == null) {
            return new MenuItem(menuItemDTO.getId(), menuItemDTO.getName(), menuItemDTO.getIngredients(), menuItemDTO.getImage(),
                    menuItemDTO.getDescription(), false, new Price(), menuService.findOne(menuItemDTO.getId()),
                    menuItemDTO.getPreparationTime());
        }
        return new MenuItem(menuItemDTO.getId(), menuItemDTO.getName(), menuItemDTO.getIngredients(), menuItemDTO.getImage(),
                menuItemDTO.getDescription(), false, priceDTOToPrice.convert(menuItemDTO.getPrice()),
                menuService.findOne(menuItemDTO.getId()), menuItemDTO.getPreparationTime());
    }
}
