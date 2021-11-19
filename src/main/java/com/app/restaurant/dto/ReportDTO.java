package com.app.restaurant.dto;

import com.app.restaurant.model.Report;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class ReportDTO {

    private Integer id;

    private double income;

    private double expense;

    public ReportDTO(Report r){
        income = r.getIncome();
        expense = r.getExpense();
    }

}
