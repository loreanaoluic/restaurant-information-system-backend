package com.app.restaurant.support;

import com.app.restaurant.dto.ReceiptItemDTO;
import com.app.restaurant.model.ReceiptItem;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReceiptItemDTOToReceiptItem implements Converter<ReceiptItemDTO, ReceiptItem> {

    private final ReceiptDTOToReceipt receiptDTOToReceipt;

    public ReceiptItemDTOToReceiptItem(ReceiptDTOToReceipt receiptDTOToReceipt) {
        this.receiptDTOToReceipt = receiptDTOToReceipt;
    }

    @Override
    public ReceiptItem convert(ReceiptItemDTO receiptItemDTO) {
        return new ReceiptItem(receiptItemDTO.getQuantity(), receiptItemDTO.getAdditionalNote(),
                receiptItemDTO.getItemStatus(), receiptDTOToReceipt.convert(receiptItemDTO.getReceipt()));
    }
}
