package com.app.restaurant.model;


import lombok.*;
import com.app.restaurant.model.enums.ReceiptItemStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @EqualsAndHashCode(exclude = {"id", "price"})
public class MenuItem extends Item {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;


    private double preparationTime;
    public MenuItem(Integer id, String ingredients, String image, String description, Price price, Menu menu) {
        super(id, ingredients, image, description, price);
        this.menu = menu;
    }

    public MenuItem(Integer id, Menu menu, Double preparationTime){
        this.setId(id);
        this.menu = menu;
        this.preparationTime = preparationTime;
    }
}
