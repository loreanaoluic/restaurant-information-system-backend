package com.app.restaurant.support;

import com.app.restaurant.dto.ExpenseDTO;
import com.app.restaurant.model.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ExpenseDTOToExpense implements Converter<ExpenseDTO, Expense> {



    @Override
    public Expense convert(ExpenseDTO source) {
        Expense exp = new Expense();
        exp.setDeleted(source.isDeleted());
        exp.setDate(source.getDate());
        exp.setId(source.getId());
        exp.setText(source.getText());
        exp.setValue(source.getValue());

        return exp;
    }
}
