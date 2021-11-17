package com.app.restaurant.repository;

import com.app.restaurant.model.DrinkCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkCardRepository extends JpaRepository<DrinkCard, Integer> {
}
