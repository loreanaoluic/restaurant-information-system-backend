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
public class DrinkCardItem extends Item {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drink_card_id")
    private DrinkCard drinkCard;

    public DrinkCardItem(Integer id, String ingredients, String image, String description, Price price, DrinkCard drinkCard) {
        super(id, ingredients, image, description, price);
        this.drinkCard = drinkCard;
    }

    public DrinkCardItem(Integer id, DrinkCard drinkCard){
        this.setId(id);
        this.drinkCard = drinkCard;
    }
}
