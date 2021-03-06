package com.app.restaurant.service;

import com.app.restaurant.model.MenuItem;

public interface IMenuItemService extends IGenericService<MenuItem> {

    MenuItem update(MenuItem menuItem, Integer id) throws Exception;

    void delete(MenuItem menuItem) throws Exception;
}
