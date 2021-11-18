package com.app.restaurant.dto;

import com.app.restaurant.model.enums.ReceiptItemStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class ReceiptItemDTO {

    private Integer id;
    private int quantity;
    private String additionalNote;
    private ReceiptItemStatus itemStatus;
    private ReceiptDTO receipt;
    private Integer itemId;
}
