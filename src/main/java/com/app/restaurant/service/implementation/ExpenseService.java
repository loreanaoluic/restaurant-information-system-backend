package com.app.restaurant.service.implementation;

import com.app.restaurant.exception.InvalidValueException;
import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.Expense;
import com.app.restaurant.repository.ExpenseRepository;
import com.app.restaurant.service.IExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ExpenseService implements IExpenseService {

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

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
    public List<Expense> getByDate(long date) {
        return expenseRepository.findByDate(date);
    }

    @Override
    public List<Expense> getByDates(long start_date, long end_date) {
        return expenseRepository.findByDates(start_date, end_date);
    }

    @Override
    public void delete(Integer id) throws Exception {
        Expense e = this.findOne(id);

        if (e == null) {
            throw new NotFoundException("Expense with given id does not exist.");
        }

        e.setDeleted(true);
        this.save(e);

    }
    @Override
    public Expense create(Expense e) throws Exception{
        if(e.getText().equals(""))
            throw new InvalidValueException("");
        this.save(e);

        return e;
    }

    @Override
    public Expense update(Expense e) throws Exception {

        Expense exp = this.findOne(e.getId());

        if (exp == null) {
            throw new NotFoundException("Expense with given id does not exist.");
        }

        exp.setValue(e.getValue());

        exp.setText(e.getText());

        exp.setDeleted(e.isDeleted());

        exp.setDate(e.getDate());

        return expenseRepository.save(exp);

    }

    @Override
    public double calculateValue(List<Expense> expenses) {

        if (expenses == null) {
            throw new NotFoundException("Expenses with given id does not exist.");
        }

        double value = 0;

        for (Expense e: expenses) {
            value += e.getValue();
        }
        return value;
    }
}
