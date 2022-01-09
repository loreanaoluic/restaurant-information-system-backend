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

    @Override
    public void delete(Integer id) {
        RestaurantTable restaurantTable = this.findOne(id);
        restaurantTable.setDeleted(true);
        this.save(restaurantTable);
    }

    @Override
    public RestaurantTable update(RestaurantTable restaurantTable) {
        RestaurantTable found = this.findOne(restaurantTable.getId());
        found.setTableStatus(restaurantTable.getTableStatus());
        found.setTableShape(restaurantTable.getTableShape());
        found.setCoordinateX(restaurantTable.getCoordinateX());
        found.setCoordinateY(restaurantTable.getCoordinateY());
        found.setDeleted(restaurantTable.getDeleted());
        this.save(found);
        return found;
    }
}
