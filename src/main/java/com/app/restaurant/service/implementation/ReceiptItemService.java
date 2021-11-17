package com.app.restaurant.service.implementation;

import com.app.restaurant.model.ReceiptItem;
import com.app.restaurant.model.enums.ReceiptItemStatus;
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
        return receiptItemRepository.findAll();
    }

    @Override
    public ReceiptItem findOne(Integer id) {
        return receiptItemRepository.findById(id).orElse(null);
    }

    @Override
    public ReceiptItem save(ReceiptItem receiptItem) {
        return receiptItemRepository.save(receiptItem);
    }

    @Override
    public ReceiptItem changeStatusToReady(Integer receiptItemId) {
        ReceiptItem receiptItem = this.findOne(receiptItemId);

        if (receiptItem == null) {
            return null;
        }

        if (receiptItem.getItemStatus().equals(ReceiptItemStatus.ORDERED)) {
            receiptItem.setItemStatus(ReceiptItemStatus.READY);
            this.save(receiptItem);
            return receiptItem;
        }

        return null;
    }
}
