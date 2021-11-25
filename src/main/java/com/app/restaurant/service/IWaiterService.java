package com.app.restaurant.service;

import com.app.restaurant.model.ReceiptItem;
import com.app.restaurant.model.users.Waiter;

public interface IWaiterService {

    void delete(Integer id);

    Waiter create(Waiter entity) throws Exception;

    Waiter update(Waiter entity) throws Exception;

    void newReceipt(Integer tableId);

    boolean newOrder(ReceiptItem receiptItem, Integer tableId, Integer receiptId) throws Exception;
}
