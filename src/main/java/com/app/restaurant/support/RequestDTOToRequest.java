package com.app.restaurant.support;

import com.app.restaurant.dto.RequestDTO;
import com.app.restaurant.model.Request;
import com.app.restaurant.service.implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RequestDTOtoRequest implements Converter<RequestDTO, Request> {

    private final UserService userService;

    @Autowired
    public RequestDTOtoRequest(UserService userService) {
        this.userService = userService;
    }


    @Override
    public Request convert(RequestDTO dto) {
        Request request = new Request();

        request.setId(dto.getId());
        request.setIngredients(dto.getIngredients());
        request.setItemName(dto.getItemName());
        request.setDescription(dto.getDescription());
        request.setImage(dto.getImage());
        request.setPrice(dto.getPrice());
        request.setPreparationTime(dto.getPreparationTime());
        request.setUser(this.userService.findOne(dto.getUserID()));

        return request;
    }
}
