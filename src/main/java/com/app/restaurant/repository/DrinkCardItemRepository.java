package com.app.restaurant.repository;

import com.app.restaurant.model.DrinkCardItem;
import com.app.restaurant.model.users.Waiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrinkCardItemRepository extends JpaRepository<DrinkCardItem, Integer> {

    @Query("select drinkCardItem from DrinkCardItem drinkCardItem where drinkCardItem.deleted = false" )
    List<DrinkCardItem> findAll();
}
