package com.app.restaurant.service;

import com.app.restaurant.dto.UserDTO;
import com.app.restaurant.model.users.Manager;
import com.app.restaurant.model.users.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends UserDetailsService ,IGenericService<User>{
    public List<User> findAll();

    void delete(Integer id);

    void deleteByUsername(String username);

    User findOne(Integer id);

    User findByUsername(String username);

    User save(User entity);

    User update(User user) throws Exception;

    User create(User entity) throws Exception;

    User getLoggedInUser();

    User createDynamicUser(UserDTO dto) throws Exception;

    User updateDynamicUser(UserDTO dto) throws Exception;
}
