package com.app.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class ReceiptDTO {
    private Integer id;
    private List<ReceiptItemDTO> itemDTOs;
    private long issueDate;
}
