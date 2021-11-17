package com.app.restaurant.service.implementation;

import com.app.restaurant.model.DrinkCardItem;
import com.app.restaurant.repository.DrinkCardItemRepository;
import com.app.restaurant.service.IDrinkCardItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrinkCardItemService implements IDrinkCardItemService {

    private final DrinkCardItemRepository drinkCardItemRepository;

    @Autowired
    public DrinkCardItemService(DrinkCardItemRepository drinkCardItemRepository) {
        this.drinkCardItemRepository = drinkCardItemRepository;
    }

    @Override
    public List<DrinkCardItem> findAll() {
        return drinkCardItemRepository.findAll();
    }

    @Override
    public DrinkCardItem findOne(Integer id) {
        return drinkCardItemRepository.findById(id).orElse(null);
    }

    @Override
    public DrinkCardItem save(DrinkCardItem drinkCardItem) {
        return drinkCardItemRepository.save(drinkCardItem);
    }

    @Override
    public DrinkCardItem update(DrinkCardItem drinkCardItem, Integer id) {
        DrinkCardItem updated = this.findOne(id);

        if (updated == null) {
            return null;
        }

        updated.setIngredients(drinkCardItem.getIngredients());
        updated.setImage(drinkCardItem.getImage());
        updated.setDescription(drinkCardItem.getDescription());

        drinkCardItemRepository.save(updated);
        return updated;
    }
}
