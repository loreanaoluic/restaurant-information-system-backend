package com.app.restaurant.repository;

import com.app.restaurant.model.DrinkCardItem;
import com.app.restaurant.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {

    @Query("select menuItem from MenuItem menuItem where menuItem.deleted = false" )
    List<MenuItem> findAll();
}
