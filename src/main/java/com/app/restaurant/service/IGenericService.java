package com.app.restaurant.service;

import java.util.List;

public interface IGenericService<T> {

    List<T> findAll();

    T findOne(Integer id);

    T save(T entity);
}
