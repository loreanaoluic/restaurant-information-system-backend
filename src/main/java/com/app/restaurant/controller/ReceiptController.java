package com.app.restaurant.controller;

import com.app.restaurant.dto.ReceiptDTO;
import com.app.restaurant.service.implementation.ReceiptService;
import com.app.restaurant.support.ReceiptDTOToReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;
    private final ReceiptDTOToReceipt receiptDTOtoReceipt;

    @Autowired
    public ReceiptController(ReceiptService receiptService, ReceiptDTOToReceipt receiptDTOtoReceipt) {
        this.receiptService = receiptService;
        this.receiptDTOtoReceipt = receiptDTOtoReceipt;
    }

    @GetMapping("/one-receipt/{id}")
    @PreAuthorize("hasAuthority('ROLE_WAITER')")
    public ResponseEntity<?> getOneReceipt(@PathVariable Integer id){
        return new ResponseEntity<>(this.receiptService.findOne(id), HttpStatus.OK);
    }

    @PostMapping("/update-receipt")
    @PreAuthorize("hasAuthority('ROLE_WAITER')")
    public ResponseEntity<?> updateReceipt(@RequestBody ReceiptDTO dto) throws Exception {
        this.receiptService.updateReceipt(this.receiptDTOtoReceipt.convert(dto));

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
