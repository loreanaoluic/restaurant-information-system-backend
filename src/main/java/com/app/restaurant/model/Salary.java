package com.app.restaurant.model;

import com.app.restaurant.model.users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Salary {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "value", nullable = false)
    private double value;

    @Column(name = "start_date", nullable = false)
    private long startDate;

    private long endDate;

    @OneToOne
    @JsonIgnore
    private User user;

    public Salary(Double value,long startDate,User user) {
        this.value=value;
        this.startDate= System.currentTimeMillis();
        this.user=user;
    }
}
