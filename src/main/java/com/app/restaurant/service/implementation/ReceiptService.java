package com.app.restaurant.service.implementation;

import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.Receipt;
import com.app.restaurant.model.ReceiptItem;
import com.app.restaurant.repository.ReceiptRepository;
import com.app.restaurant.service.IReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReceiptService implements IReceiptService {

    private final ReceiptRepository receiptRepository;

    @Autowired
    public ReceiptService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }


    @Override
    public List<Receipt> findAll() {
        return this.receiptRepository.findAll();
    }

    @Override
    public Receipt findOne(Integer id) {
        return this.receiptRepository.findById(id).orElse(null);
    }

    @Override
    public Receipt save(Receipt entity) {
        return this.receiptRepository.save(entity);
    }

    @Override
    public List<ReceiptItem> findAllReceiptItems(Integer id) {

        Receipt receipt = this.findOne(id);
        if (receipt == null) {
            throw new NotFoundException("Receipt with given id does not exist.");
        }

        List<ReceiptItem> receiptItems = new ArrayList<>();
        for (ReceiptItem receiptItem : receipt.getReceiptItems()) {
            if (!receiptItem.getDeleted()) {
                receiptItems.add(receiptItem);
                System.out.println("VRIJEDNOST "+receiptItem.getQuantity());
            }
        }
        return receiptItems;
    }

    @Override
    public Receipt updateReceipt(Receipt receipt) {
        if (this.findOne(receipt.getId()) != null){
            receiptRepository.save(receipt);
            return receipt;
        }
        throw new NotFoundException("Receipt with given id does not exist.");
    }

    @Override
    public List<Receipt> findByDates(long start_date, long end_date) {
        return receiptRepository.findByDates(start_date, end_date);
    }

    @Override
    public List<Receipt> findByDate(long date){
        return receiptRepository.findByDate(date);
    }

    @Override
    public double calculateValue(List<Receipt> receipts) {
        double value = 0;

        for (Receipt receipt : receipts){
            for (ReceiptItem receiptItem : receipt.getReceiptItems()) {
                value += (receiptItem.getQuantity() * receiptItem.getItem().getPrice().getValue());

            }
        }
        return value;
    }
}
