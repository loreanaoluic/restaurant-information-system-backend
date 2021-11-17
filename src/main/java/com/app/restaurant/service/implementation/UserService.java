package com.app.restaurant.service.implementation;

import com.app.restaurant.model.users.User;
import com.app.restaurant.repository.UserRepository;
import com.app.restaurant.service.IGenericService;
import com.app.restaurant.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
        return null;
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
    public User update(User user) {
        Optional<User> u=userRepository.findById(user.getId());
        if(u.isPresent()==true) {
            User updateUser = user;
            userRepository.save(updateUser);
        }
        return user;
    }

    @Override
    public User create(User entity) throws Exception {
        if (userRepository.findByUsername(entity.getUsername()) != null)
            throw new Exception("User already exists.");
        else
            userRepository.save(entity);

        return entity;
    }
}
