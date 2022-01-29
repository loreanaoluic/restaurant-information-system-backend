package com.app.restaurant.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString(callSuper=true, exclude = {"menu"})
public class MenuItem extends Item {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    @JsonIgnore
    private Menu menu;

    private double preparationTime;

    public MenuItem(Integer id, String name, String ingredients, String image, String description, Boolean deleted,
                    Price price, Menu menu, double preparationTime) {
        super(id, name, ingredients, image, description, deleted, price);
        this.menu = menu;
        this.preparationTime = preparationTime;
    }

    public MenuItem(String name, String ingredients, String image, String description, Price price, Menu menu,
                    double preparationTime) {
        super(name, ingredients, image, description, price);
        this.menu = menu;
        this.preparationTime = preparationTime;
    }

    public MenuItem(Integer id, Menu menu, Double preparationTime){
        this.setId(id);
        this.menu = menu;
        this.preparationTime = preparationTime;
    }
}
