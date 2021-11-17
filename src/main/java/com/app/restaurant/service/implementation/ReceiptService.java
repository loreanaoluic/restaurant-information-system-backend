package com.app.restaurant.service.implementation;

import com.app.restaurant.model.Receipt;
import com.app.restaurant.repository.ReceiptRepository;
import com.app.restaurant.service.IReceiptService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiptService implements IReceiptService {

    private final ReceiptRepository receiptRepository;

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
}
