package com.app.restaurant.support;

import com.app.restaurant.dto.MenuItemDTO;
import com.app.restaurant.model.MenuItem;
import com.app.restaurant.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

@Component
public class MenuItemDTOToMenuItem implements Converter<MenuItemDTO, MenuItem> {

    private final IMenuService menuService;

    @Autowired
    public MenuItemDTOToMenuItem(IMenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public MenuItem convert(MenuItemDTO menuItemDTO) {
        return new MenuItem(menuItemDTO.getId(), menuService.findOne(menuItemDTO.getId()));
    }
}
