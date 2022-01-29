package com.app.restaurant.service;

import com.app.restaurant.model.RestaurantTable;

public interface IRestaurantTableService extends IGenericService<RestaurantTable> {

    void delete(Integer id) throws Exception;

    RestaurantTable update(RestaurantTable restaurantTable) throws Exception;
}
