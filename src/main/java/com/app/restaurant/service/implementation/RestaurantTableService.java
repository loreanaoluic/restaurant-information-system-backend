package com.app.restaurant.service.implementation;

import com.app.restaurant.model.RestaurantTable;
import com.app.restaurant.repository.RestaurantTableRepository;
import com.app.restaurant.service.IRestaurantTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantTableService implements IRestaurantTableService {

    private final RestaurantTableRepository restaurantTableRepository;

    @Autowired
    public RestaurantTableService(RestaurantTableRepository restaurantTableRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
    }


    @Override
    public List<RestaurantTable> findAll() {
        return restaurantTableRepository.findAll();
    }

    @Override
    public RestaurantTable findOne(Integer id) {
        return restaurantTableRepository.findById(id).orElse(null);
    }

    @Override
    public RestaurantTable save(RestaurantTable restaurantTable) {
        return restaurantTableRepository.save(restaurantTable);
    }
}
