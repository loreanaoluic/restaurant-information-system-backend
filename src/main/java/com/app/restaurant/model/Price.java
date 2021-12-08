package com.app.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Price {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "value", nullable = false)
    private double value;

    private long startDate;

    private long endDate;

    @OneToOne
    @JsonIgnore
    private Item item;

    public Price(double value, long startDate, Item item) {
        this.value = value;
        this.startDate = startDate;
        this.item = item;
    }

    public Price(Integer id, double value, long startDate, long endDate) {
        this.id = id;
        this.value = value;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Price(Integer id) {
        this.id = id;
    }
}
