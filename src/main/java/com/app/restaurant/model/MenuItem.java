package com.app.restaurant.model;

import com.app.restaurant.model.enums.ReceiptItemStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class MenuItem extends Item {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    public MenuItem(Integer id, String ingredients, String image, String description, Price price, Menu menu) {
        super(id, ingredients, image, description, price);
        this.menu = menu;
    }
}
