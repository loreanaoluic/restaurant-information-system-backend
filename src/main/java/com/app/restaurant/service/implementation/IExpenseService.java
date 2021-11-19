package com.app.restaurant.service.implementation;


import com.app.restaurant.dto.ExpenseDTO;
import com.app.restaurant.model.Expense;
import com.app.restaurant.service.IGenericService;

import java.util.List;

public interface IExpenseService extends IGenericService<Expense> {

    List<Expense> getAll();
    List<Expense> getByDate(long date);
    List<Expense> getByDates(long start_date, long end_date);
    void delete(Integer id);
    Expense create(Expense e);
    Expense update(Expense e);


}
