package com.app.restaurant.controller;

import com.app.restaurant.dto.UserDTO;
import com.app.restaurant.model.Role;
import com.app.restaurant.model.users.*;
import com.app.restaurant.repository.UserRepository;
import com.app.restaurant.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/user")
public class UserContoller {

    private final IUserService userService;

    public UserContoller(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasAuthority('ROLE_WAITER')")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username){
        return new ResponseEntity<>(this.userService.findByUsername(username), HttpStatus.OK);
    }

    @PostMapping(value = "/new-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) throws Exception {
        return new ResponseEntity<>(this.userService.createDynamicUser(userDTO),HttpStatus.OK);
    }

    @PostMapping(value = "/update-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) throws Exception {
        return new ResponseEntity<>(this.userService.updateDynamicUser(userDTO),HttpStatus.OK);
    }
}
