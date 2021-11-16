package com.app.restaurant.service.implementation;

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
        return priceRepository.findAll();
    }

    @Override
    public Price findOne(Integer id) {
        return priceRepository.findById(id).orElse(null);
    }

    @Override
    public Price save(Price price) {
        return priceRepository.save(price);
    }
}
