package com.app.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name="expenses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Where(clause = "deleted = false")
public class Expense {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Expense(String text, double value, long date, boolean deleted){
        this.text =text;
        this.value = value;
        this.date = date;
        this.deleted = deleted;
    }
    private String text;
    private double value;
    private long date;
    private boolean deleted;
}
