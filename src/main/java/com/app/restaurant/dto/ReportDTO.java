package com.app.restaurant.dto;

import com.app.restaurant.model.Report;

public class ReportDTO {

    private Integer id;

    private double income;

    private double expense;

    private long date;

    public ReportDTO(){

    }
    public ReportDTO(Report r){
        id = r.getId();
        income = r.getIncome();
        expense = r.getExpense();
        date = r.getDate();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
