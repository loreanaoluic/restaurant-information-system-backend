package com.app.restaurant.service;

import com.app.restaurant.model.Receipt;

public interface IReceiptService extends IGenericService<Receipt>{
    int updateReceipt(Receipt receipt);
}
