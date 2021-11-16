package com.app.restaurant.model;

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

    @OneToMany(mappedBy = "restaurantTable", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Receipt> receipt;

    @OneToMany(mappedBy = "restaurantTable", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Notification> notifications;
}
