package com.app.restaurant.service;

import com.app.restaurant.model.Price;

public interface IPriceService extends IGenericService<Price> {

    Price update(Price price, Integer id);

}
