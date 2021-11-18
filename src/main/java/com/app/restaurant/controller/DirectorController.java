package com.app.restaurant.controller;

import com.app.restaurant.model.users.User;
import com.app.restaurant.repository.UserRepository;
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
@RequestMapping(value = "/api/director")
public class DirectorController {

    private final UserRepository userRepository;

    public DirectorController(UserRepository userRepository) {
        this.userRepository = userRepository;
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
