package com.app.restaurant.service.implementation;

import com.app.restaurant.model.DrinkCardItem;
import com.app.restaurant.model.MenuItem;
import com.app.restaurant.model.ReceiptItem;
import com.app.restaurant.model.enums.ReceiptItemStatus;
import com.app.restaurant.repository.ReceiptItemRepository;
import com.app.restaurant.service.IReceiptItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReceiptItemService implements IReceiptItemService {

    private final ReceiptItemRepository receiptItemRepository;
    private final UserService userService;

    @Autowired
    public ReceiptItemService(ReceiptItemRepository receiptItemRepository, UserService userService) {
        this.receiptItemRepository = receiptItemRepository;
        this.userService = userService;
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
    public ReceiptItem changeStatusToReady(Integer receiptItemId, Integer userId) {
        ReceiptItem receiptItem = this.findOne(receiptItemId);

        if (receiptItem == null) {
            return null;
        }

        if (receiptItem.getItemStatus().equals(ReceiptItemStatus.ORDERED)) {
            receiptItem.setItemStatus(ReceiptItemStatus.READY);
            receiptItem.setAuthor(userService.findOne(userId));
            this.save(receiptItem);
            return receiptItem;
        }

        return null;
    }

    @Override
    public List<ReceiptItem> cookOrders() {
        List<ReceiptItem> orders = new ArrayList<>();

        for (ReceiptItem receiptItem : this.findAll()) {
            if (receiptItem.getItem() instanceof MenuItem && receiptItem.getItemStatus().equals(ReceiptItemStatus.ORDERED)) {
                orders.add(receiptItem);
            }
        }

        return orders;
    }

    @Override
    public List<ReceiptItem> bartenderOrders() {
        List<ReceiptItem> orders = new ArrayList<>();

        for (ReceiptItem receiptItem : this.findAll()) {
            if (receiptItem.getItem() instanceof DrinkCardItem && receiptItem.getItemStatus().equals(ReceiptItemStatus.ORDERED)) {
                orders.add(receiptItem);
            }
        }

        return orders;
    }
}
