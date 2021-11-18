package com.app.restaurant.service.implementation;

import com.app.restaurant.model.Receipt;
import com.app.restaurant.model.ReceiptItem;
import com.app.restaurant.model.RestaurantTable;
import com.app.restaurant.model.enums.TableStatus;
import com.app.restaurant.model.users.Waiter;
import com.app.restaurant.repository.WaiterRepository;
import com.app.restaurant.service.IWaiterService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WaiterService implements IWaiterService {

    private final WaiterRepository waiterRepository;
    private final ReceiptService receiptService;
    private final ReceiptItemService receiptItemService;
    private final RestaurantTableService restaurantTableService;

    public WaiterService(WaiterRepository waiterRepository, ReceiptService receiptService, ReceiptItemService receiptItemService, RestaurantTableService restaurantTableService) {
        this.waiterRepository = waiterRepository;
        this.receiptService = receiptService;
        this.receiptItemService = receiptItemService;
        this.restaurantTableService = restaurantTableService;
    }

    @Override
    public void delete(Integer id) {
        Waiter waiter = waiterRepository.getById(id);
        waiterRepository.delete(waiter);
    }

    @Override
    public Waiter create(Waiter entity) throws Exception {

        if (waiterRepository.findByUsername(entity.getUsername()) != null)
            throw new Exception("Waiter already exists.");
        else
            waiterRepository.save(entity);

        return entity;
    }

    @Override
    public Waiter update(Waiter entity) throws Exception {
        Optional<Waiter> man = waiterRepository.findById(entity.getId());
        if(man.isPresent()==true){
            Waiter waiter=man.get();
            waiter.setName(entity.getName());
            waiter.setLastName(entity.getLastName());
            waiter.setEmailAddress(entity.getEmailAddress());
            waiter.setUsername(entity.getUsername());
            waiterRepository.save(waiter);
        }
        return man.get();
    }

    @Override
    public void newReceipt(Integer tableId) {
        Receipt receipt  = new Receipt();
        receipt.setIssueDate(System.currentTimeMillis());
        List<ReceiptItem> receiptItems = new ArrayList<>();
        receipt.setReceiptItems(receiptItems);
        receiptService.save(receipt);

        RestaurantTable restaurantTable = restaurantTableService.findOne(tableId);
        restaurantTable.setReceipt(receipt);
        restaurantTable.setTableStatus(TableStatus.OCCUPIED);
        restaurantTableService.save(restaurantTable);
    }

    @Override
    public boolean newOrder(ReceiptItem receiptItem, Integer tableId, Integer receiptId) {

        Receipt receipt = receiptService.findOne(receiptId);
        List<ReceiptItem> receiptItems = receipt.getReceiptItems();
        receiptItems.add(receiptItem);
        receipt.setReceiptItems(receiptItems);
        receiptService.save(receipt);

        return true;
    }
}
