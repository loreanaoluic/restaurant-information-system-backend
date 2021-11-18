package com.app.restaurant.dto;

import com.app.restaurant.model.enums.ReceiptItemStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class ReceiptItemDTO {
    private Integer id;
    private Integer quantity;
    private String additionalNote;
    private ReceiptItemStatus status;
    private Integer receiptId;
    private Integer itemId;
}
