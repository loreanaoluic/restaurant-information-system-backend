package com.app.restaurant.controller;

import com.app.restaurant.dto.RequestDTO;
import com.app.restaurant.model.Request;
import com.app.restaurant.service.IRequestService;
import com.app.restaurant.support.RequestDTOtoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/head-bartender")
public class HeadBartenderController {

    private final IRequestService requestService;

    private final RequestDTOtoRequest requestDTOtoRequest;

    @Autowired
    public HeadBartenderController(IRequestService requestService, RequestDTOtoRequest requestDTOtoRequest) {
        this.requestService = requestService;
        this.requestDTOtoRequest = requestDTOtoRequest;
    }

    @PostMapping(value = "/new-request", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createRequest(@RequestBody RequestDTO requestDTO) {
        Request request = requestService.createRequest(requestDTOtoRequest.convert(requestDTO));

        if(request != null) {
            return new ResponseEntity<>(request, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
