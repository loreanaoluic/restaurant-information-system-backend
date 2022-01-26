package com.app.restaurant.controller;

import com.app.restaurant.dto.*;
import com.app.restaurant.model.ReceiptItem;
import com.app.restaurant.model.users.Waiter;
import com.app.restaurant.service.*;
import com.app.restaurant.model.DrinkCardItem;
import com.app.restaurant.model.MenuItem;
import com.app.restaurant.support.DrinkCardItemDTOToDrinkCardItem;
import com.app.restaurant.support.MenuItemDTOToMenuItem;
import com.app.restaurant.support.ReceiptItemDTOToReceiptItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/waiter")
public class WaiterController {

    private final IMenuItemService menuItemService;
    private final IDrinkCardItemService drinkCardItemService;
    private final IWaiterService waiterService;
    private final IReceiptItemService receiptItemService;
    private final IReceiptService receiptService;

    private final DrinkCardItemDTOToDrinkCardItem drinkCardItemDTOToDrinkCardItem;
    private final MenuItemDTOToMenuItem menuItemDTOToMenuItem;
    private final ReceiptItemDTOToReceiptItem receiptItemDTOToReceiptItem;

    @Autowired
    public WaiterController(IMenuItemService menuItemService, IDrinkCardItemService drinkCardItemService,
                            IWaiterService waiterService, IReceiptService receiptService,
                            IReceiptItemService receiptItemService, DrinkCardItemDTOToDrinkCardItem drinkCardItemDTOToDrinkCardItem, MenuItemDTOToMenuItem menuItemDTOToMenuItem, ReceiptItemDTOToReceiptItem receiptItemDTOToReceiptItem) {
        this.menuItemService = menuItemService;
        this.drinkCardItemService = drinkCardItemService;
        this.waiterService = waiterService;
        this.receiptService = receiptService;
        this.receiptItemService = receiptItemService;
        this.drinkCardItemDTOToDrinkCardItem = drinkCardItemDTOToDrinkCardItem;
        this.menuItemDTOToMenuItem = menuItemDTOToMenuItem;
        this.receiptItemDTOToReceiptItem = receiptItemDTOToReceiptItem;
    }

    @GetMapping(value = "/all-menu-items", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_WAITER', 'ROLE_MANAGER')")
    public ResponseEntity<List<MenuItem>> getMenuItems() {
        return new ResponseEntity<>(menuItemService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/all-drink-card-items", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_WAITER', 'ROLE_MANAGER')")
    public ResponseEntity<List<DrinkCardItem>> getDrinkCardItems() {
        return new ResponseEntity<>(drinkCardItemService.findAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/new-waiter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<?> createWaiter(@RequestBody WaiterDTO waiterDTO) {
        Waiter waiter = null;
        try {
            Waiter man = new Waiter(waiterDTO);
            waiter = waiterService.create(man);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(waiter != null) {
            return new ResponseEntity<>(waiter, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/update-waiter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_WAITER', 'ROLE_MANAGER')")
    public ResponseEntity<?> updateWaiter(@RequestBody WaiterDTO waiterDTO) {
        Waiter waiter = null;
        try {
            waiter = waiterService.update(new Waiter(waiterDTO));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(waiter != null) {
            return new ResponseEntity<>(waiter, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/order/{table-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_WAITER')")
    public ResponseEntity<?> newReceipt(@PathVariable("table-id") Integer tableId) {
        return new ResponseEntity<>(waiterService.newReceipt(tableId), HttpStatus.CREATED);
    }

    @PostMapping(value = "/order-drink/{table-id}/{receipt-id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_WAITER')")
    public ResponseEntity<?> addDrinkToReceipt(@PathVariable("table-id") Integer tableId, @PathVariable("receipt-id") Integer receiptId,
                                               @RequestBody DrinkCardItemDTO drinkCardItemDTO) throws Exception {
        waiterService.addItemToReceipt(drinkCardItemDTOToDrinkCardItem.convert(drinkCardItemDTO), tableId, receiptId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/order-meal/{table-id}/{receipt-id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_WAITER')")
    public ResponseEntity<?> addMealToReceipt(@PathVariable("table-id") Integer tableId,
                                              @PathVariable("receipt-id") Integer receiptId,
                                              @RequestBody MenuItemDTO menuItemDTO) throws Exception {
        waiterService.addItemToReceipt(menuItemDTOToMenuItem.convert(menuItemDTO), tableId, receiptId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/receipt-items/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_WAITER')")
    public ResponseEntity<List<ReceiptItem>> getReceiptItems( @PathVariable("id") Integer id) {
        return new ResponseEntity<>(receiptService.findAllReceiptItems(id), HttpStatus.OK);
    }

    @GetMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_WAITER')")
    public ResponseEntity<List<ReceiptItem>> getOrders() {
        return new ResponseEntity<>(receiptItemService.waiterOrders(), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/change-status", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_WAITER')")
    public ResponseEntity<?> changeStatus(@PathVariable("id") Integer id) throws Exception {
        ReceiptItem receiptItem = receiptItemService.changeStatusToDone(id);

        if (receiptItem == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(receiptItem, HttpStatus.OK);
    }

    @PostMapping("/update-receipt-item")
    @PreAuthorize("hasAuthority('ROLE_WAITER')")
    public ResponseEntity<?> updateReceiptItem(@RequestBody ReceiptItemDTO receiptItemDTO) {

        return new ResponseEntity<>(this.receiptItemService.updateReceiptItemNote(this.receiptItemDTOToReceiptItem.convert(receiptItemDTO)),
                HttpStatus.OK);
    }

    @PostMapping("/delete-receipt-item/{id}")
    @PreAuthorize("hasAuthority('ROLE_WAITER')")
    public ResponseEntity<?> deleteReceiptItem(@PathVariable Integer id) {

        this.receiptItemService.deleteReceiptItem(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
