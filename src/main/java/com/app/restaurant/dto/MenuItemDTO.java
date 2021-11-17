package com.app.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class MenuItemDTO {

    private Integer id;
    private String name;
    private String ingredients;
    private String image;
    private String description;
    private PriceDTO price;
    private Integer menuId;
    private double preparationTime;
}
