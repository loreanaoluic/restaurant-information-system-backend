package com.app.restaurant.service.implementation;

import com.app.restaurant.model.Expense;
import com.app.restaurant.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional

@Service
public class ExpenseService implements  IExpenseService{

    @Autowired
    private ExpenseRepository expenseRepository;


    @Override
    public List<Expense> findAll() {
        return expenseRepository.findAll();
    }

    @Override
    public Expense findOne(Integer id) {
        return expenseRepository.findById(id).orElse(null);
    }

    @Override
    public Expense save(Expense entity) {
        return expenseRepository.save(entity);
    }


    @Override
    public List<Expense> getAll() {
        return expenseRepository.findAll();
    }

    @Override
    public List<Expense> getByDate(long date) {
        return expenseRepository.findByDate(date);
    }

    @Override
    public List<Expense> getByDates(long start_date, long end_date) {
        return expenseRepository.findByDates(start_date, end_date);
    }

    @Override
    public void delete(Integer id) {
        Expense e = expenseRepository.getById(id);
        e.setDeleted(true);
        expenseRepository.save(e);

    }
    @Override
    public Expense create(Expense e){

        expenseRepository.save(e);

        return e;
    }

    @Override
    public Expense update(Expense e) {

        Expense exp = expenseRepository.getById(e.getId());
        exp.setValue(e.getValue());

        exp.setText(e.getText());

        exp.setDeleted(e.isDeleted());

        exp.setDate(e.getDate());

        return expenseRepository.save(exp);

    }

    public double calculateValue(List<Expense> expenses){
        double value = 0;

        for (Expense e: expenses
             ) {
            value += e.getValue();
        }
        return value;
    }



}
