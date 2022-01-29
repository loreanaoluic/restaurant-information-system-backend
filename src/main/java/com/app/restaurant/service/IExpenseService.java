package com.app.restaurant.service;


import com.app.restaurant.model.Expense;

import java.util.List;

public interface IExpenseService extends IGenericService<Expense> {

    List<Expense> getByDate(long date);
    List<Expense> getByDates(long start_date, long end_date);
    void delete(Integer id) throws Exception;
    Expense create(Expense e) throws Exception;
    Expense update(Expense e) throws Exception;
    double calculateValue(List<Expense> expenses);

}
