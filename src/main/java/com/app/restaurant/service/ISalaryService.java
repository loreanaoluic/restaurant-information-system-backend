package com.app.restaurant.service;

import com.app.restaurant.model.Salary;

import java.util.List;

public interface ISalaryService extends IGenericService<Salary> {

    List<Salary> findByDates(long start_date, long end_date);

    double calculateValue(List<Salary> salaries, long start_date, long end_date);
}
