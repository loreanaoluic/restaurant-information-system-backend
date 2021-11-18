package com.app.restaurant.controller;

import com.app.restaurant.dto.*;
import com.app.restaurant.model.DrinkCardItem;
import com.app.restaurant.model.MenuItem;
import com.app.restaurant.model.Role;
import com.app.restaurant.model.Salary;
import com.app.restaurant.model.users.*;
import com.app.restaurant.repository.UserRepository;
import com.app.restaurant.service.IManagerService;
import com.app.restaurant.service.IRequestService;
import com.app.restaurant.service.IUserService;
import com.app.restaurant.support.DrinkCardItemDTOToDrinkCardItem;
import com.app.restaurant.support.ManagerDTOToManager;
import com.app.restaurant.support.MenuItemDTOToMenuItem;
import com.app.restaurant.support.PriceDTOToPrice;
import com.app.restaurant.support.RequestDTOtoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/manager")
public class ManagerController {

    private final IManagerService managerService;
    private final IRequestService requestService;
    private final IUserService userService;
    private final UserRepository userRepository;

    private final MenuItemDTOToMenuItem menuItemDTOToMenuItem;
    private final RequestDTOtoRequest requestDTOtoRequest;
    private final DrinkCardItemDTOToDrinkCardItem drinkCardItemDTOToDrinkCardItem;
    private final ManagerDTOToManager managerDTOToManager;
    private final PriceDTOToPrice priceDTOToPrice;

    @Autowired
    public ManagerController(IManagerService managerService, MenuItemDTOToMenuItem menuItemDTOToMenuItem,
                             RequestDTOtoRequest requestDTOtoRequest, IRequestService requestService,
                             IUserService userService, UserRepository userRepository, DrinkCardItemDTOToDrinkCardItem drinkCardItemDTOToDrinkCardItem, PriceDTOToPrice priceDTOToPrice, ManagerDTOToManager managerDTOToManager) {

        this.managerService = managerService;
        this.menuItemDTOToMenuItem = menuItemDTOToMenuItem;
        this.requestDTOtoRequest = requestDTOtoRequest;
        this.requestService = requestService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.drinkCardItemDTOToDrinkCardItem = drinkCardItemDTOToDrinkCardItem;

        this.managerDTOToManager = managerDTOToManager;

        this.priceDTOToPrice = priceDTOToPrice;
    }


    @PostMapping(value = "/new-menu-item", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createMenuItem(@RequestBody MenuItemDTO menuItemDTO) {
        MenuItem menuItem = managerService.createNewMenuItem(menuItemDTOToMenuItem.convert(menuItemDTO), menuItemDTO.getPrice().getValue());

        if (menuItem != null) {
            return new ResponseEntity<>(menuItem, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/new-drink-card-item", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createDrinkCardItem(@RequestBody DrinkCardItemDTO drinkCardItemDTO) {
        DrinkCardItem drinkCardItem = managerService.createNewDrinkCardItem(drinkCardItemDTOToDrinkCardItem.convert(drinkCardItemDTO),
                drinkCardItemDTO.getPrice().getValue());

        if (drinkCardItem != null) {
            return new ResponseEntity<>(drinkCardItem, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/update-menu-item", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateMenuItem(@RequestBody MenuItemDTO menuItemDTO) {
        MenuItem menuItem = managerService.updateMenuItem(menuItemDTOToMenuItem.convert(menuItemDTO));

        if (menuItem != null) {
            return new ResponseEntity<>(menuItem, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/update-drink-card-item", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateDrinkCardItem(@RequestBody DrinkCardItemDTO drinkCardItemDTO) {
        DrinkCardItem drinkCardItem = managerService.updateDrinkCardItem(drinkCardItemDTOToDrinkCardItem.convert(drinkCardItemDTO));

        if (drinkCardItem != null) {
            return new ResponseEntity<>(drinkCardItem, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @PostMapping(value = "/new-manager", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createManager(@RequestBody ManagerDTO managerDTO) {
        Manager manager = null;
        try {
            Manager man = managerDTOToManager.convert(managerDTO);
            manager = managerService.create(man);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (manager != null) {
            return new ResponseEntity<>(manager, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/request-approval", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> requestReview(@RequestBody RequestReviewDTO reviewDto) {

        if (reviewDto.isApproved()) {
            requestService.createItem(this.requestDTOtoRequest.convert(reviewDto.getDto()));
            return new ResponseEntity<>("Request has been accepted", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Request has been denied", HttpStatus.OK);
    }

    @PostMapping(value = "/update-manager", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateManager(@RequestBody ManagerDTO managerDTO) {
        Manager manager = null;
        try {
            manager = managerService.update(managerDTOToManager.convert(managerDTO));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(manager != null) {
            return new ResponseEntity<>(manager, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/update-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) {
        User user = null;
        try {
            User u = null;
            switch(userDTO.getDtype()) {
                case "Manager":
                    u=new Manager();
                    u.setRole(new Role(2,"Manager"));
                    break;
                case "Director":
                    u=new Director();
                    u.setRole(new Role(1,"Director"));
                    break;
                case "Bartender":
                    u=new Bartender();
                    u.setRole(new Role(6,"Bartender"));
                    break;
                case "Chef":
                    u=new Chef();
                    u.setRole(new Role(3,"Chef"));
                    break;
                case "Cook":
                    u=new Cook();
                    u.setRole(new Role(2,"Cook"));
                    break;
                case "HeadBartender":
                    u=new HeadBartender();
                    u.setRole(new Role(5,"HeadBartender"));
                    break;
                case "Waiter":
                    u=new Waiter();
                    u.setRole(new Role(7,"Waiter"));
                    break;
            }
            User tmp= userRepository.findByUsername(userDTO.getUsername());

            u.setId(tmp.getId());
            u.setName(tmp.getName());
            u.setLastName(tmp.getLastName());
            u.setEmailAddress(tmp.getEmailAddress());
            u.setUsername(tmp.getUsername());
            u.setPassword(tmp.getPassword());
            u.setDeleted(tmp.getDeleted());

            tmp.setDeleted(true);
            tmp=userService.update(tmp);
            user = userService.update(u);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(user != null) {
            return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @PostMapping(value = "/update-salary/{id}/{value}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateSalary(@PathVariable("id") Integer id,@PathVariable("value") Integer value) {
        User user=null;
        try {
            Optional<User> tmp= userRepository.findById(id);
            if(tmp.isPresent()){
                user=tmp.get();
                user.setSalary(new Salary(value,System.currentTimeMillis(),user));
                user=userService.update(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(user != null) {
            return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/getAll",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsers() {
        List<User> users=userRepository.findAll();

        if(users != null) {
            return new ResponseEntity<>(users, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
