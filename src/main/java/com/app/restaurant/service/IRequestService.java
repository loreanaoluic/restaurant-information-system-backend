package com.app.restaurant.service;

import com.app.restaurant.model.Request;

import java.util.List;

public interface IRequestService extends IGenericService<Request>{

    List<Request> findAllNotDeleted();

    Integer createItem(Request request) throws Exception;

    Request createRequest(Request request);

    void deleteRequest(Request request);
}
