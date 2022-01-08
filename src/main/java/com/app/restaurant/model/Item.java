package com.app.restaurant.model;

import com.app.restaurant.model.enums.ReceiptItemStatus;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString(exclude = {"price"})
public abstract class Item {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String ingredients;

    private String image;

    private String description;

    private Boolean deleted = Boolean.FALSE;

    @OneToOne(cascade = CascadeType.ALL)
    private Price price;

    public Item(String name, String ingredients, String image, String description, Price price){
        this.name =name;
        this.ingredients = ingredients;
        this.image = image;
        this.description = description;
        this.price = price;
    }

}
