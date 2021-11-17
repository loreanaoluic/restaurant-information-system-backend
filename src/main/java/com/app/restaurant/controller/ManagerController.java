package com.app.restaurant.controller;

import com.app.restaurant.dto.DrinkCardItemDTO;
import com.app.restaurant.dto.MenuItemDTO;
import com.app.restaurant.dto.RequestReviewDTO;
import com.app.restaurant.model.DrinkCardItem;
import com.app.restaurant.model.MenuItem;
import com.app.restaurant.service.IManagerService;
import com.app.restaurant.service.IRequestService;
import com.app.restaurant.service.implementation.DrinkCardItemService;
import com.app.restaurant.service.implementation.MenuItemService;
import com.app.restaurant.service.IPriceService;
import com.app.restaurant.support.DrinkCardItemDTOToDrinkCardItem;
import com.app.restaurant.support.MenuItemDTOToMenuItem;
import com.app.restaurant.support.RequestDTOtoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/manager")
public class ManagerController {

    private final IManagerService managerService;
    private final IPriceService priceService;
    private final MenuItemDTOToMenuItem menuItemDTOToMenuItem;
    private final RequestDTOtoRequest requestDTOtoRequest;
    private final IRequestService requestService;
    private final MenuItemService menuItemService;
    private final DrinkCardItemService drinkCardItemService;
    private final DrinkCardItemDTOToDrinkCardItem drinkCardItemDTOToDrinkCardItem;

    @Autowired
    public ManagerController(IManagerService managerService, IPriceService priceService, MenuItemDTOToMenuItem menuItemDTOToMenuItem, RequestDTOtoRequest requestDTOtoRequest, IRequestService requestService, MenuItemService menuItemService, DrinkCardItemService drinkCardItemService, DrinkCardItemDTOToDrinkCardItem drinkCardItemDTOToDrinkCardItem) {
        this.managerService = managerService;
        this.priceService = priceService;
        this.menuItemDTOToMenuItem = menuItemDTOToMenuItem;
        this.requestDTOtoRequest = requestDTOtoRequest;
        this.requestService = requestService;
        this.menuItemService = menuItemService;
        this.drinkCardItemService = drinkCardItemService;
        this.drinkCardItemDTOToDrinkCardItem = drinkCardItemDTOToDrinkCardItem;
    }


    @PostMapping(value = "/new-menu-item", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createMenuItem(@RequestBody MenuItemDTO menuItemDTO) {
        MenuItem menuItem = managerService.createNewMenuItem(menuItemDTOToMenuItem.convert(menuItemDTO), menuItemDTO.getPrice());

        if(menuItem != null) {
            return new ResponseEntity<>(menuItem, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/new-drink-card-item", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createDrinkCardItem(@RequestBody DrinkCardItemDTO drinkCardItemDTO) {
        DrinkCardItem drinkCardItem = managerService.createNewDrinkCardItem(drinkCardItemDTOToDrinkCardItem.convert(drinkCardItemDTO), drinkCardItemDTO.getPrice());

        if(drinkCardItem != null) {
            return new ResponseEntity<>(drinkCardItem, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/update-menu-item", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateMenuItem(@RequestBody MenuItemDTO menuItemDTO) {
        MenuItem menuItem = managerService.updateMenuItem(menuItemDTOToMenuItem.convert(menuItemDTO), menuItemDTO.getPrice());

        if(menuItem != null) {
            return new ResponseEntity<>(menuItem, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/update-drink-card-item", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateDrinkCardItem(@RequestBody DrinkCardItemDTO drinkCardItemDTO) {
        DrinkCardItem drinkCardItem = managerService.updateDrinkCardItem(drinkCardItemDTOToDrinkCardItem.convert(drinkCardItemDTO), drinkCardItemDTO.getPrice());

        if(drinkCardItem != null) {
            return new ResponseEntity<>(drinkCardItem, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/request-approval", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> requestReview(@RequestBody RequestReviewDTO reviewDto){

        if(reviewDto.isApproved()){
            requestService.createItem(this.requestDTOtoRequest.convert(reviewDto.getDto()));
            return new ResponseEntity<>("Request has been accepted" , HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Request has been denied", HttpStatus.OK);
    }
}
