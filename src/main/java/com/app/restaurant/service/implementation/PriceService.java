package com.app.restaurant.service.implementation;

import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.Price;
import com.app.restaurant.repository.PriceRepository;
import com.app.restaurant.service.IPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceService implements IPriceService {

    private final PriceRepository priceRepository;

    @Autowired
    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public List<Price> findAll() {
        return this.priceRepository.findAll();
    }

    @Override
    public Price findOne(Integer id) {
        return this.priceRepository.findById(id).orElse(null);
    }

    @Override
    public Price save(Price entity) {
        return this.priceRepository.save(entity);
    }

    @Override
    public Price update(Price price, Integer id) throws Exception {
        Price updated = this.findOne(id);

        if (updated == null) {
            throw new NotFoundException("Price with given id does not exist.");
        }

        updated.setValue(price.getValue());
        updated.setStartDate(price.getStartDate());
        updated.setEndDate(price.getEndDate());
        updated.setItem(price.getItem());

        priceRepository.save(updated);
        return updated;
    }
}
