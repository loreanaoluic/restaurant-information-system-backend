package com.app.restaurant.service.implementation;

import com.app.restaurant.exception.DuplicateEntityException;
import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.*;
import com.app.restaurant.model.enums.ReceiptItemStatus;
import com.app.restaurant.model.enums.TableStatus;
import com.app.restaurant.model.users.User;
import com.app.restaurant.model.users.Waiter;
import com.app.restaurant.repository.WaiterRepository;
import com.app.restaurant.service.IWaiterService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WaiterService implements IWaiterService {

    private final WaiterRepository waiterRepository;
    private final ReceiptService receiptService;
    private final RestaurantTableService restaurantTableService;
    private final ReceiptItemService receiptItemService;

    public WaiterService(WaiterRepository waiterRepository, ReceiptService receiptService, RestaurantTableService restaurantTableService, ReceiptItemService receiptItemService) {
        this.waiterRepository = waiterRepository;
        this.receiptService = receiptService;
        this.restaurantTableService = restaurantTableService;
        this.receiptItemService = receiptItemService;
    }

    @Override
    public List<Waiter> findAll() {
        return this.waiterRepository.findAll();
    }

    @Override
    public Waiter findOne(Integer id) {
        return this.waiterRepository.findById(id).orElse(null);
    }

    @Override
    public Waiter save(Waiter entity) throws Exception {
        return this.waiterRepository.save(entity);
    }

    @Override
    public void delete(Integer id) {
        Waiter waiter = this.findOne(id);
        if (waiter == null) {
            throw new NotFoundException("Waiter with given id does not exist.");
        }
        waiterRepository.delete(waiter);
    }

    @Override
    public Waiter create(Waiter entity) throws Exception {

        if (waiterRepository.findByUsername(entity.getUsername()) != null)
            throw new DuplicateEntityException("Waiter with given username already exists.");
        else
            this.save(entity);

        return entity;
    }

    @Override
    public Waiter update(Waiter entity) throws Exception {
        Waiter waiter = this.findOne(entity.getId());
        if (waiter != null) {
            waiter.setName(entity.getName());
            waiter.setLastName(entity.getLastName());
            waiter.setEmailAddress(entity.getEmailAddress());
            waiter.setUsername(entity.getUsername());
            this.save(waiter);
        }
        else
            throw new NotFoundException("Waiter with given username does not exist.");
        return waiter;
    }

    @Override
    public Receipt newReceipt(Integer tableId) {
        Receipt receipt = new Receipt();
        receipt.setIssueDate(System.currentTimeMillis());
        List<ReceiptItem> receiptItems = new ArrayList<>();
        receipt.setReceiptItems(receiptItems);
        receiptService.save(receipt);

        RestaurantTable restaurantTable = restaurantTableService.findOne(tableId);

        if (restaurantTable == null) {
            throw new NotFoundException("Table with given id does not exist.");
        }

        restaurantTable.setReceipt(receipt);
        restaurantTable.setTableStatus(TableStatus.OCCUPIED);
        restaurantTableService.save(restaurantTable);
        return receipt;
    }

    @Override
    public void addItemToReceipt(Item item, Integer receiptId) {

        Receipt receipt = receiptService.findOne(receiptId);

        if (receipt == null) {
            throw new NotFoundException("Receipt with given id does not exist.");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        List<ReceiptItem> receiptItems = receipt.getReceiptItems();

        boolean exists = false;
        for (ReceiptItem receiptItem : receiptItems) {
            if (receiptItem.getItem().getName().equals(item.getName())) {
                int value = receiptItem.getQuantity() + 1;
                receiptItem.setQuantity(value);
                exists = true;
            }
        }

        if (receiptItems.size() == 0 || !exists) {
            ReceiptItem newReceiptItem = new ReceiptItem(1, ReceiptItemStatus.ORDERED, receipt, item, user);
            receiptItems.add(newReceiptItem);
            receiptItemService.save(newReceiptItem);
        }

        receipt.setReceiptItems(receiptItems);
        receiptService.save(receipt);
    }
}
