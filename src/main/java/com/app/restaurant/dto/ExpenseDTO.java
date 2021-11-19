package com.app.restaurant.dto;


import com.app.restaurant.model.Expense;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExpenseDTO {

    private Integer id;

    private String text;
    private double value;
    private long date;
    private boolean deleted;
    public ExpenseDTO(Expense e){
        this.text = e.getText();
        this.value = e.getValue();
        this.date = e.getDate();
        this.id = e.getId();
        this.deleted = e.isDeleted();
    }
}
