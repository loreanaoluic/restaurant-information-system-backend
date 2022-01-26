package com.app.restaurant.controller;

import com.app.restaurant.dto.*;
import com.app.restaurant.model.DrinkCardItem;
import com.app.restaurant.model.MenuItem;
import com.app.restaurant.model.RestaurantTable;
import com.app.restaurant.model.Salary;
import com.app.restaurant.model.users.Manager;
import com.app.restaurant.model.users.User;
import com.app.restaurant.repository.UserRepository;
import com.app.restaurant.service.*;
import com.app.restaurant.support.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final IDrinkCardItemService drinkCardItemService;
    private final IMenuItemService menuItemService;
    private final IRestaurantTableService restaurantTableService;

    private final MenuItemDTOToMenuItem menuItemDTOToMenuItem;
    private final DrinkCardItemDTOToDrinkCardItem drinkCardItemDTOToDrinkCardItem;
    private final ManagerDTOToManager managerDTOToManager;
    private final UserToUserDTO userToUserDTO;

    @Autowired
    public ManagerController(IManagerService managerService, MenuItemDTOToMenuItem menuItemDTOToMenuItem,
                             IRequestService requestService, IUserService userService, UserRepository userRepository,
                             IDrinkCardItemService drinkCardItemService, IMenuItemService menuItemService, IRestaurantTableService restaurantTableService, DrinkCardItemDTOToDrinkCardItem drinkCardItemDTOToDrinkCardItem,
                             ManagerDTOToManager managerDTOToManager, UserToUserDTO userToUserDTO)
    {


        this.managerService = managerService;
        this.menuItemDTOToMenuItem = menuItemDTOToMenuItem;
        this.requestService = requestService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.drinkCardItemService = drinkCardItemService;
        this.menuItemService = menuItemService;
        this.restaurantTableService = restaurantTableService;
        this.drinkCardItemDTOToDrinkCardItem = drinkCardItemDTOToDrinkCardItem;

        this.managerDTOToManager = managerDTOToManager;
        this.userToUserDTO = userToUserDTO;

    }


    @PostMapping(value = "/new-menu-item", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<?> createMenuItem(@RequestBody MenuItemDTO menuItemDTO) throws Exception {
        MenuItem menuItem = managerService.createNewMenuItem(menuItemDTOToMenuItem.convert(menuItemDTO), menuItemDTO.getPrice().getValue());

        if (menuItem != null) {
            return new ResponseEntity<>(menuItem, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/new-drink-card-item", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<?> createDrinkCardItem(@RequestBody DrinkCardItemDTO drinkCardItemDTO) {
        DrinkCardItem drinkCardItem = managerService.createNewDrinkCardItem(drinkCardItemDTOToDrinkCardItem.convert(drinkCardItemDTO),
                drinkCardItemDTO.getPrice().getValue());

        if (drinkCardItem != null) {
            return new ResponseEntity<>(drinkCardItem, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/delete-user/{username}")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        userService.deleteByUsername(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/delete-drink-card-item", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<?> deleteDrinkCardItem(@RequestBody DrinkCardItemDTO drinkCardItemDTO) {
        drinkCardItemService.delete(drinkCardItemDTOToDrinkCardItem.convert(drinkCardItemDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/delete-menu-item", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<?> deleteMenuItem(@RequestBody MenuItemDTO menuItemDTO) {
        menuItemService.delete(menuItemDTOToMenuItem.convert(menuItemDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/update-menu-item", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<?> updateMenuItem(@RequestBody MenuItemDTO menuItemDTO) throws Exception {
        MenuItem menuItem = managerService.updateMenuItem(menuItemDTOToMenuItem.convert(menuItemDTO));

        if (menuItem != null) {
            return new ResponseEntity<>(menuItem, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/update-drink-card-item", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<?> updateDrinkCardItem(@RequestBody DrinkCardItemDTO drinkCardItemDTO) throws Exception {
        DrinkCardItem drinkCardItem = managerService.updateDrinkCardItem(drinkCardItemDTOToDrinkCardItem.convert(drinkCardItemDTO));

        if (drinkCardItem != null) {
            return new ResponseEntity<>(drinkCardItem, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @PostMapping(value = "/new-manager", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
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

    @GetMapping(value = "/requests", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<?> getAllRequests() {

        return new ResponseEntity<>(this.requestService.findAllNotDeleted(), HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/request-approved/{id}")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<?> requestApproved(@PathVariable Integer id) throws Exception {

        requestService.createItem(this.requestService.findOne(id));
        return new ResponseEntity<>("Request has been accepted", HttpStatus.CREATED);
    }

    @PostMapping(value = "/request-declined/{id}")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<?> requestDeclined(@PathVariable Integer id) {

        requestService.deleteRequest(this.requestService.findOne(id));
        return new ResponseEntity<>("Request has been denied", HttpStatus.OK);
    }

    @PostMapping(value = "/update-manager", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
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
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) throws Exception {
        userService.updateDynamicUser(userDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/update-salary/{id}/{value}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<?> updateSalary(@PathVariable("id") Integer id,@PathVariable("value") Double value) {
        User user = null;
        try {
            Optional<User> tmp = userRepository.findById(id);
            if(tmp.isPresent()){
                user = tmp.get();
                user.setSalary(new Salary(value,System.currentTimeMillis(),user));
                user = userService.update(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(user != null) {
            return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER', 'ROLE_DIRECTOR')")
    public ResponseEntity<?> getUsers() {
        List<User> users=userRepository.findAll();

        if(users != null) {
            return new ResponseEntity<>(this.userToUserDTO.convert(users), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<List<UserDTO>>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/restaurant-tables", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER', 'ROLE_WAITER')")
    public ResponseEntity<?> getRestaurantTables() {
        return new ResponseEntity<>(restaurantTableService.findAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/add-restaurant-table", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<?> addRestaurantTable(@RequestBody RestaurantTable restaurantTable) throws Exception {
        return new ResponseEntity<>(restaurantTableService.save(restaurantTable), HttpStatus.OK);
    }

    @PostMapping(value = "/delete-restaurant-table/{id}")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<?> deleteRestaurantTable(@PathVariable Integer id) throws Exception {
        restaurantTableService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/update-restaurant-table", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<?> updateRestaurantTable(@RequestBody RestaurantTable restaurantTable) throws Exception {
        restaurantTableService.update(restaurantTable);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
