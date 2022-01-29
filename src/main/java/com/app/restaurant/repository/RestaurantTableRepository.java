package com.app.restaurant.repository;

import com.app.restaurant.model.RestaurantTable;
import com.app.restaurant.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Integer> {

    @Query("select table from RestaurantTable table where table.deleted = false" )
    List<RestaurantTable> findAll();
}
