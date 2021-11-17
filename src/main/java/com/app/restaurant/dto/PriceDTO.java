package com.app.restaurant.dto;

import com.app.restaurant.model.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class PriceDTO {
    private double value;
    private long startDate;
    private long endDate;
    private Item item;
}
