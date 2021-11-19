package com.app.restaurant.service.implementation;

import com.app.restaurant.model.Receipt;
import com.app.restaurant.model.ReceiptItem;
import com.app.restaurant.repository.ReceiptRepository;
import com.app.restaurant.service.IReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiptService implements IReceiptService {
    @Autowired
    private final ReceiptRepository receiptRepository;

    private final ReceiptItemService receiptItemService;

    @Autowired
    public ReceiptService(ReceiptRepository receiptRepository, ReceiptItemService receiptItemService) {
        this.receiptRepository = receiptRepository;
        this.receiptItemService = receiptItemService;
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
    public int updateReceipt(Receipt receipt) {
        if(this.findOne(receipt.getId()) != null){

            this.receiptRepository.save(receipt);

            return 0;
        }


        return 1;
    }


    public List<Receipt> findByDates(long start_date, long end_date){
        return receiptRepository.findByDates(start_date, end_date);
    }

    public List<Receipt> findByDate(long date){
        return receiptRepository.findByDate(date);
    }
}
