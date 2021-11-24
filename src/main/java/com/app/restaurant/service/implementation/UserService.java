package com.app.restaurant.service.implementation;

import com.app.restaurant.exception.DuplicateEntityException;
import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.users.User;
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
        if (u.isPresent()) {
            userRepository.save(user);
        }
        throw new NotFoundException("User does not exist.");
    }

    @Override
    public User create(User entity) throws Exception {
        if (userRepository.findByUsername(entity.getUsername()) != null)
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

}
