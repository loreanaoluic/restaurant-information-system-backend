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
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) throws Exception {
        return new ResponseEntity<>(this.userService.createDynamicUser(userDTO),HttpStatus.OK);
    }

    @PostMapping(value = "/update-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) throws Exception {
        return new ResponseEntity<>(this.userService.updateDynamicUser(userDTO),HttpStatus.OK);
    }

    /* //LOGICKO BRISANJE, IMAMO VEC IMPLEMENTIRANO FIZICKO TAKO DA JE OVO VISAK :D
    @PostMapping(value = "/delete-user/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id) {

        Optional<User> tmp= userRepository.findById(id);
        if(tmp.isPresent()){
            User user=tmp.get();
            user.setDeleted(true);
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }*/
}
