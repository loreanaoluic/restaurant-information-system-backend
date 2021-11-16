package com.app.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class DrinkCardItemDTO {
    private Integer id;
    private String ingredients;
    private String image;
    private String description;
    private double price;
    private Integer drinkCardId;
}
