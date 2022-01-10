package com.app.restaurant.model;

import com.app.restaurant.model.enums.TableShape;
import com.app.restaurant.model.enums.TableStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class RestaurantTable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "table_status", nullable = false)
    private TableStatus tableStatus;

    private TableShape tableShape;

    private double coordinateX;

    private double coordinateY;

    private Boolean deleted = Boolean.FALSE;

    @OneToOne
    private Receipt receipt;

}
