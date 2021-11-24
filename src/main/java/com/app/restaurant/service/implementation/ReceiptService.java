package com.app.restaurant.service.implementation;

import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.Receipt;
import com.app.restaurant.model.ReceiptItem;
import com.app.restaurant.repository.ReceiptRepository;
import com.app.restaurant.service.IReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void updateReceipt(Receipt receipt) throws Exception {
        if(this.findOne(receipt.getId()) != null){

            this.save(receipt);
            return;
        }
        throw new NotFoundException("Receipt with given id does not exist.");
    }


    public List<Receipt> findByDates(long start_date, long end_date){
        return receiptRepository.findByDates(start_date, end_date);
    }

    public List<Receipt> findByDate(long date){
        return receiptRepository.findByDate(date);
    }

    public double calculateValue(List<Receipt> receipts){
        double value = 0;
        for (Receipt r: receipts
        ) {

            for (ReceiptItem rr:r.getReceiptItems()
            ) {

                value += (rr.getQuantity()*rr.getItem().getPrice().getValue());
            }

        }
        return value;
    }
}
