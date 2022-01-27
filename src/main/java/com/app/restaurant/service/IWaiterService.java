package com.app.restaurant.service;

import com.app.restaurant.model.*;
import com.app.restaurant.model.users.Waiter;

public interface IWaiterService extends IGenericService<Waiter> {

    void delete(Integer id);

    Waiter create(Waiter entity) throws Exception;

    Waiter update(Waiter entity) throws Exception;

    Receipt newReceipt(Integer tableId) throws Exception;

    void addItemToReceipt(Item item, Integer receiptId) throws Exception;
}
