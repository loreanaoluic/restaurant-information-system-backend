package com.app.restaurant.service.implementation;

import com.app.restaurant.model.DrinkCard;
import com.app.restaurant.repository.DrinkCardRepository;
import com.app.restaurant.service.IDrinkCardService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrinkCardService implements IDrinkCardService {

    private final DrinkCardRepository drinkCardRepository;

    public DrinkCardService(DrinkCardRepository drinkCardRepository) {
        this.drinkCardRepository = drinkCardRepository;
    }


    @Override
    public List<DrinkCard> findAll() {
        return this.drinkCardRepository.findAll();
    }

    @Override
    public DrinkCard findOne(Integer id) {
        return this.drinkCardRepository.findById(id).orElse(null);
    }

    @Override
    public DrinkCard save(DrinkCard entity) {
        return this.drinkCardRepository.save(entity);
    }
}
