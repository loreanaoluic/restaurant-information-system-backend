package com.app.restaurant.controller;

import com.app.restaurant.dto.ManagerDTO;
import com.app.restaurant.dto.WaiterDTO;
import com.app.restaurant.model.users.Manager;
import com.app.restaurant.model.users.Waiter;
import com.app.restaurant.service.IWaiterService;
import com.app.restaurant.service.implementation.WaiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/waiter")
public class WaiterController {

    private final IWaiterService waiterService;

    @Autowired
    public WaiterController(IWaiterService waiterService) {
        this.waiterService = waiterService;
    }

    @PostMapping(value = "/new-waiter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createManager(@RequestBody WaiterDTO waiterDTO) {
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
    public ResponseEntity<?> updateManager(@RequestBody WaiterDTO waiterDTO) {
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
}
