package com.app.restaurant.service;

import com.app.restaurant.model.Request;

public interface IRequestService extends IGenericService<Request>{
    Integer createItem(Request request);
}
