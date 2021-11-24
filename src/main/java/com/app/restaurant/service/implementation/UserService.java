package com.app.restaurant.service.implementation;

import com.app.restaurant.dto.UserDTO;
import com.app.restaurant.exception.DuplicateEntityException;
import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.Role;
import com.app.restaurant.model.users.*;
import com.app.restaurant.repository.UserRepository;
import com.app.restaurant.service.IGenericService;
import com.app.restaurant.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService , IGenericService<User> {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);

        for(User u : this.userRepository.findAll()){
            System.out.println(u);
        }

        if (user == null) {
            throw new UsernameNotFoundException("No user found for "+ s + ".");
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User findOne(Integer id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public User save(User entity) {
        return this.userRepository.save(entity);
    }

    @Override
    public User update(User user) throws Exception {
        Optional<User> u = userRepository.findById(user.getId());
        System.out.println(user);
        if (u.isPresent()) {
            userRepository.save(user);
        }else
            throw new NotFoundException("User does not exist.");

        return user;
    }

    @Override
    public User create(User entity) throws Exception {
        if (userRepository.findByUsername(entity.getUsername()) != null && !entity.getDeleted())
            throw new DuplicateEntityException("User already exists.");
        else
            userRepository.save(entity);

        return entity;
    }

	@Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken)
            return null;

//        User user = (User) authentication.getPrincipal();
        User user = this.userRepository.findByUsername(authentication.getName());
        return user;
    }

    @Override
    public User setDynamicUser(UserDTO dto) throws Exception {
        User u = null;
        switch(dto.getDtype()) {
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
        User tmp= userRepository.findByUsername(dto.getUsername());

        u.setId(tmp.getId());
        u.setName(dto.getName());
        u.setLastName(dto.getLastName());
        u.setEmailAddress(dto.getEmailAddress());
        u.setUsername(dto.getUsername());
        u.setPassword(dto.getPassword());
        u.setDeleted(dto.getDeleted());

        this.userRepository.delete(tmp);
        this.create(u);

        return u;
    }


}
