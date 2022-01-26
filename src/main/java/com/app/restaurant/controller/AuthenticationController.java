package com.app.restaurant.controller;

import com.app.restaurant.dto.UserTokenState;
import com.app.restaurant.model.users.User;
import com.app.restaurant.service.implementation.UserService;
import com.app.restaurant.dto.UserDTO;
import com.app.restaurant.security.auth.JwtAuthenticationRequest;
import com.app.restaurant.support.UserToUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.app.restaurant.security.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

@RestController
@CrossOrigin  //(origins = {"http://localhost:3000", "https://pharmacy-tim9.herokuapp.com", "https://pharmacy9.herokuapp.com"})
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    private final TokenUtils tokenUtils;
    private final AuthenticationManager authenticationManager;

    private final UserService userService;
    private final UserToUserDTO userToUserDTO;

    @Autowired
    public AuthenticationController(TokenUtils t, AuthenticationManager aM, UserService us,
                                    UserToUserDTO userToUserDTO){
        this.tokenUtils = t;
        this.authenticationManager = aM;
        this.userService = us;
        this.userToUserDTO = userToUserDTO;
    }


    // Prvi endpoint koji pogadja korisnik kada se loguje.
    // Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) {

        UsernamePasswordAuthenticationToken u = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                authenticationRequest.getPassword());


        //
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(u);
        } catch(BadCredentialsException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }


        // Ubaci korisnika u trenutni security kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

    // U slucaju isteka vazenja JWT tokena, endpoint koji se poziva da se token osvezi
    @PostMapping(value = "/refresh")
    public ResponseEntity<UserTokenState> refreshAuthenticationToken(HttpServletRequest request) {

        String token = tokenUtils.getToken(request);
        String username = this.tokenUtils.getUsernameFromToken(token);
        User user = (User) this.userService.loadUserByUsername(username);

        if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = tokenUtils.refreshToken(token);
            int expiresIn = tokenUtils.getExpiredIn();
            return ResponseEntity.ok(new UserTokenState(refreshedToken, expiresIn));
        } else {
            UserTokenState userTokenState = new UserTokenState();
            return ResponseEntity.badRequest().body(userTokenState);
        }
    }

    @GetMapping("/getLoggedIn")
    public ResponseEntity<UserDTO> getLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        User user = (User) authentication.getPrincipal();
        if (user == null)
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);

        UserDTO dto = userToUserDTO.convert(user);

        return new ResponseEntity<>(dto,  HttpStatus.OK);

    }

    @GetMapping("/getPasswordResetDate/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Timestamp> getPasswordResetDate(@PathVariable Integer id){
        User user = this.userService.findOne(id);
        return new ResponseEntity<>(user.getLastPasswordResetDate(), HttpStatus.OK);
    }

    /*@PostMapping("/changePassword/{id}")
    public ResponseEntity<Boolean> changePassword(@PathVariable Long id, @RequestBody String newPassword){
        // TODO do we need to check if id is same as logged in user?
        newPassword = newPassword.substring(0, newPassword.length() - 1);

        return new ResponseEntity<>(this.userService.changePassword(id, passwordEncoder.encode(newPassword)),
                                    HttpStatus.OK);
    }*/

    @GetMapping(value = "/logOut", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity logoutUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)){
            SecurityContextHolder.clearContext();

            return new ResponseEntity<>("You successfully logged out!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User is not authenticated!", HttpStatus.BAD_REQUEST);
        }

    }
}
