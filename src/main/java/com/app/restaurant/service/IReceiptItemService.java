package com.app.restaurant.service;

import com.app.restaurant.model.ReceiptItem;

import java.util.List;

public interface IReceiptItemService extends IGenericService<ReceiptItem> {

    ReceiptItem changeStatusToReady (Integer receiptItemId) throws Exception;

    List<ReceiptItem> cookOrders();

    List<ReceiptItem> bartenderOrders();
}
