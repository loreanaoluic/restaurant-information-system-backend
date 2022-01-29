package com.app.restaurant.support;

import com.app.restaurant.dto.UserDTO;
import com.app.restaurant.model.users.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserToUserDTO implements Converter<User, UserDTO> {
    @Override
    public UserDTO convert(User user) {
        UserDTO dto = new UserDTO();
        dto.setDeleted(user.getDeleted());
        dto.setEmailAddress(user.getEmailAddress());
        dto.setLastName(user.getLastName());
        dto.setName(user.getName());
        dto.setPassword(user.getPassword());
        dto.setUsername(user.getUsername());
        if(user.getSalary() != null) dto.setSalary(user.getSalary().getValue());
        String dtype = "";

        if(user instanceof Manager) dtype = "Manager";
        if(user instanceof Director) dtype = "Director";
        if(user instanceof Bartender) dtype = "Bartender";
        if(user instanceof Cook) dtype = "Cook";
        if(user instanceof Waiter) dtype = "Waiter";
        if(user instanceof HeadBartender) dtype = "HeadBartender";
        if(user instanceof Chef) dtype = "Chef";

        dto.setDtype(dtype);

        return dto;
    }

    public List<UserDTO> convert(List<User> users){
        List<UserDTO> dtos = new ArrayList<>();

        for(User u : users) dtos.add(this.convert(u));

        return dtos;
    }
}
