package com.app.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="reports")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Report {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Report(double i, double e){
        this.income = i;
        this.expense = e;

    }
    private double income;

    private double expense;

}
