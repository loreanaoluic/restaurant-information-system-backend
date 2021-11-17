package com.app.restaurant.model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @EqualsAndHashCode(exclude = {"id", "price"})
public class DrinkCardItem extends Item {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drink_card_id")
    private DrinkCard drinkCard;
}
