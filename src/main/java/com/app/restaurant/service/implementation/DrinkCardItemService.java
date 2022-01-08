package com.app.restaurant.service.implementation;

import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.DrinkCardItem;
import com.app.restaurant.model.Price;
import com.app.restaurant.model.users.User;
import com.app.restaurant.repository.DrinkCardItemRepository;
import com.app.restaurant.service.IDrinkCardItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrinkCardItemService implements IDrinkCardItemService {

    private final DrinkCardItemRepository drinkCardItemRepository;
    private final PriceService priceService;

    @Autowired
    public DrinkCardItemService(DrinkCardItemRepository drinkCardItemRepository, PriceService priceService) {
        this.drinkCardItemRepository = drinkCardItemRepository;
        this.priceService = priceService;
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
    public void delete(DrinkCardItem drinkCardItem) {
        drinkCardItem.setDeleted(true);
        drinkCardItemRepository.save(drinkCardItem);
    }

    @Override
    public DrinkCardItem update(DrinkCardItem drinkCardItem, Integer id) throws Exception {
        DrinkCardItem updated = this.findOne(id);

        if (updated == null) {
            throw new NotFoundException("Drink card item with given id does not exist.");
        }

        updated.setIngredients(drinkCardItem.getIngredients());
        updated.setImage(drinkCardItem.getImage());
        updated.setDescription(drinkCardItem.getDescription());
        updated.setName(drinkCardItem.getName());

        if (drinkCardItem.getPrice().getValue() != updated.getPrice().getValue()) {
            updated.setPrice(drinkCardItem.getPrice());
        }

        drinkCardItemRepository.save(updated);

        Price price = priceService.findOne(drinkCardItem.getPrice().getId());

        if (price == null) {
            throw new NotFoundException("Price with given id does not exist.");
        }

        price.setItem(updated);
        priceService.save(price);

        return updated;
    }
}
