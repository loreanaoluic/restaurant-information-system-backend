package com.app.restaurant.service;

import com.app.restaurant.model.Receipt;

public interface IReceiptService extends IGenericService<Receipt>{
    Receipt updateReceipt(Receipt receipt) throws Exception;
}
