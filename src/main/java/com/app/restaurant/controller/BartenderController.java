package com.app.restaurant.controller;

import com.app.restaurant.model.ReceiptItem;
import com.app.restaurant.service.IReceiptItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/bartender")
public class BartenderController {

    private final IReceiptItemService receiptItemService;

    @Autowired
    public BartenderController(IReceiptItemService receiptItemService) {
        this.receiptItemService = receiptItemService;
    }

    @GetMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_BARTENDER')")
    public ResponseEntity<List<ReceiptItem>> getOrders() {
        return new ResponseEntity<>(receiptItemService.bartenderOrders(), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/change-status", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_BARTENDER')")
    public ResponseEntity<?> changeStatus(@PathVariable("id") Integer id) throws Exception {
        ReceiptItem receiptItem = receiptItemService.changeStatusToReady(id);

        return new ResponseEntity<>(receiptItem, HttpStatus.OK);
    }
}
