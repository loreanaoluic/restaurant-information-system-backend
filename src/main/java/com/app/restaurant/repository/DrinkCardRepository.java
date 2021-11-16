package com.app.restaurant.repository;

import com.app.restaurant.model.DrinkCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrinkCardRepository extends JpaRepository<DrinkCard,Integer> {
}
