package com.app.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestDTO {
    private Integer id;

    private double price;

    private String ingredients;

    private String itemName;

    private String description;

    private double preparationTime;

    private String username;

    private String image;
}
