package com.app.restaurant.controller;

import com.app.restaurant.dto.ReceiptDTO;
import com.app.restaurant.service.implementation.ReceiptService;
import com.app.restaurant.support.ReceiptDTOToReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getOneReceipt(@PathVariable Integer id){
        return new ResponseEntity<>(this.receiptService.findOne(id), HttpStatus.OK);
    }

    @PostMapping("/update-receipt")
    public ResponseEntity<?> updateReceipt(@RequestBody ReceiptDTO dto){
        int result = this.receiptService.updateReceipt(this.receiptDTOtoReceipt.convert(dto));

        switch(result) {
            case 0:
                return new ResponseEntity<>("Receipt updated successfully", HttpStatus.OK);
            case 1:
                return new ResponseEntity<>( "Tried to update non-existing receipt", HttpStatus.OK);
            default:
                return new ResponseEntity<>(null, HttpStatus.OK);
        }

    }
}
