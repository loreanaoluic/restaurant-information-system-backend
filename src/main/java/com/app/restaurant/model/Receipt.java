package com.app.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="receipts")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Receipt {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "receipt", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ReceiptItem> receiptItems;

    @ManyToOne
    @JoinColumn(name = "restaurant_table_id")
    private RestaurantTable restaurantTable;
}
