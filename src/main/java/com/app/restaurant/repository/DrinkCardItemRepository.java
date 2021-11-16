package com.app.restaurant.repository;

import com.app.restaurant.model.DrinkCardItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrinkCardItemRepository extends JpaRepository<DrinkCardItem, Integer> {

}
