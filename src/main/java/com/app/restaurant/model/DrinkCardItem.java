package com.app.restaurant.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class DrinkCardItem extends Item {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drink_card_id")
    @JsonIgnore
    private DrinkCard drinkCard;

    public DrinkCardItem(Integer id, String name, String ingredients, String image, String description, Boolean deleted,
                         Price price, DrinkCard drinkCard) {
        super(id, name, ingredients, image, description, deleted, price);
        this.drinkCard = drinkCard;
    }

    public DrinkCardItem(Integer id, DrinkCard drinkCard){
        this.setId(id);
        this.drinkCard = drinkCard;
    }
}
