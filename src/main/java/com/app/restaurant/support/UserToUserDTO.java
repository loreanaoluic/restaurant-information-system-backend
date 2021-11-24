package com.app.restaurant.support;

import com.app.restaurant.dto.UserDTO;
import com.app.restaurant.model.users.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

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

        return dto;
    }
}
