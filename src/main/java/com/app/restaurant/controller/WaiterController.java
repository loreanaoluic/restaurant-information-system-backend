package com.app.restaurant.controller;

import com.app.restaurant.model.DrinkCardItem;
import com.app.restaurant.model.MenuItem;
import com.app.restaurant.service.IDrinkCardItemService;
import com.app.restaurant.service.IMenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    public WaiterController(IMenuItemService menuItemService, IDrinkCardItemService drinkCardItemService) {
        this.menuItemService = menuItemService;
        this.drinkCardItemService = drinkCardItemService;
    }

    @GetMapping(value = "/all-menu-items", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MenuItem>> getMenuItems() {
        return new ResponseEntity<>(menuItemService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/all-drink-card-items", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DrinkCardItem>> getDrinkCardItems() {
        return new ResponseEntity<>(drinkCardItemService.findAll(), HttpStatus.OK);
    }

}
