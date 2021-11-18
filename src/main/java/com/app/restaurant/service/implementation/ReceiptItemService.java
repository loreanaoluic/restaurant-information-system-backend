package com.app.restaurant.service.implementation;

import com.app.restaurant.model.ReceiptItem;
import com.app.restaurant.repository.ReceiptItemRepository;
import com.app.restaurant.service.IReceiptItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiptItemService implements IReceiptItemService {

    private final ReceiptItemRepository receiptItemRepository;

    @Autowired
    public ReceiptItemService(ReceiptItemRepository receiptItemRepository) {
        this.receiptItemRepository = receiptItemRepository;
    }


    @Override
    public List<ReceiptItem> findAll() {
        return this.receiptItemRepository.findAll();
    }

    @Override
    public ReceiptItem findOne(Integer id) {
        return this.receiptItemRepository.findById(id).orElse(null);
    }

    @Override
    public ReceiptItem save(ReceiptItem entity) {
        return this.receiptItemRepository.save(entity);
    }
}
