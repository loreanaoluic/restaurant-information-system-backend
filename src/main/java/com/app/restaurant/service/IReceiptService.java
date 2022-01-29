package com.app.restaurant.service;

import com.app.restaurant.model.Receipt;
import com.app.restaurant.model.ReceiptItem;

import java.util.List;

public interface IReceiptService extends IGenericService<Receipt>{
    Receipt updateReceipt(Receipt receipt) throws Exception;

    List<ReceiptItem> findAllReceiptItems(Integer id);

    List<Receipt> findByDates(long start_date, long end_date);

    List<Receipt> findByDate(long date);

    double calculateValue(List<Receipt> receipts);

}
