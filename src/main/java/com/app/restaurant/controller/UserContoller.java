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
    private final UserRepository userRepository;

    public UserContoller(IUserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping(value = "/new-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        User user = null;
        try {
            User u = null ;

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

            u.setName(userDTO.getName());
            u.setLastName(userDTO.getLastName());
            u.setEmailAddress(userDTO.getEmailAddress());
            u.setUsername(userDTO.getUsername());
            u.setPassword(userDTO.getPassword());
            u.setDeleted(userDTO.getDeleted());

            user = userService.create(u);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/update-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
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
            u.setName(userDTO.getName());
            u.setLastName(userDTO.getLastName());
            u.setEmailAddress(userDTO.getEmailAddress());
            u.setUsername(userDTO.getUsername());
            u.setPassword(userDTO.getPassword());
            u.setDeleted(userDTO.getDeleted());

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

    @PostMapping(value = "/delete-user/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id) {

        Optional<User> tmp= userRepository.findById(id);
        if(tmp.isPresent()){
            User user=tmp.get();
            user.setDeleted(true);
            userRepository.save(user);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
