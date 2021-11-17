package com.app.restaurant.service;

import com.app.restaurant.model.ReceiptItem;

public interface IReceiptItemService extends IGenericService<ReceiptItem> {

    ReceiptItem changeStatusToReady (Integer receiptItemId);
}
