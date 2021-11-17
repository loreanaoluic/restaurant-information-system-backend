package com.app.restaurant.model;

import com.app.restaurant.model.users.Bartender;
import com.app.restaurant.model.users.Cook;
import com.app.restaurant.model.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="notifications")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Notification {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_table_id")
    private RestaurantTable restaurantTable;

    @OneToOne
    private User author;

    private boolean done;
}
