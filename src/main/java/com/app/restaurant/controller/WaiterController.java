package com.app.restaurant.controller;

import com.app.restaurant.dto.ReceiptItemDTO;
import com.app.restaurant.dto.WaiterDTO;
import com.app.restaurant.model.users.Waiter;
import com.app.restaurant.service.IWaiterService;
import com.app.restaurant.model.DrinkCardItem;
import com.app.restaurant.model.MenuItem;
import com.app.restaurant.service.IDrinkCardItemService;
import com.app.restaurant.service.IMenuItemService;
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

    private final ReceiptItemDTOToReceiptItem receiptItemDTOToReceiptItem;

    @Autowired
    public WaiterController(IMenuItemService menuItemService, IDrinkCardItemService drinkCardItemService, IWaiterService waiterService, ReceiptItemDTOToReceiptItem receiptItemDTOToReceiptItem) {
        this.menuItemService = menuItemService;
        this.drinkCardItemService = drinkCardItemService;
        this.waiterService = waiterService;
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
            Waiter man=new Waiter(waiterDTO);
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
    @PreAuthorize("hasAuthority('ROLE_WAITER', 'ROLE_MANAGER')")
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
        waiterService.newReceipt(tableId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/order/{table-id}/{receipt-id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_WAITER')")
    public ResponseEntity<?> newOrder(@PathVariable("table-id") Integer tableId, @PathVariable("receipt-id") Integer receiptId,
                                      @RequestBody ReceiptItemDTO receiptItemDTO) throws Exception {
        waiterService.newOrder(receiptItemDTOToReceiptItem.convert(receiptItemDTO), tableId, receiptId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
