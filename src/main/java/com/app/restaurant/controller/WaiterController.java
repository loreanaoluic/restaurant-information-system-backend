package com.app.restaurant.controller;

import com.app.restaurant.dto.WaiterDTO;
import com.app.restaurant.model.users.Waiter;
import com.app.restaurant.service.IWaiterService;
import com.app.restaurant.model.DrinkCardItem;
import com.app.restaurant.model.MenuItem;
import com.app.restaurant.service.IDrinkCardItemService;
import com.app.restaurant.service.IMenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    public WaiterController(IMenuItemService menuItemService, IDrinkCardItemService drinkCardItemService, IWaiterService waiterService) {
        this.menuItemService = menuItemService;
        this.drinkCardItemService = drinkCardItemService;
        this.waiterService = waiterService;
    }

    @GetMapping(value = "/all-menu-items", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MenuItem>> getMenuItems() {
        return new ResponseEntity<>(menuItemService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/all-drink-card-items", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DrinkCardItem>> getDrinkCardItems() {
        return new ResponseEntity<>(drinkCardItemService.findAll(), HttpStatus.OK);
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
