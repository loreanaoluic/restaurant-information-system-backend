package com.app.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Report {



    private double income;

    private double expense;

}
