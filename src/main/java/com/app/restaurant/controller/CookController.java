package com.app.restaurant.controller;

import com.app.restaurant.model.MenuItem;
import com.app.restaurant.model.ReceiptItem;
import com.app.restaurant.service.IReceiptItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/cook")
public class CookController {

    private final IReceiptItemService receiptItemService;

    @Autowired
    public CookController(IReceiptItemService receiptItemService) {
        this.receiptItemService = receiptItemService;
    }

    @GetMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReceiptItem>> getOrders() {
        return new ResponseEntity<>(receiptItemService.cookOrders(), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/change-status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeStatus(@PathVariable("id") Integer id) {
        ReceiptItem receiptItem = receiptItemService.changeStatusToReady(id);

        if (receiptItem == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(receiptItem, HttpStatus.OK);
    }
}
