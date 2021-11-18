package com.app.restaurant.support;

import com.app.restaurant.dto.ReceiptDTO;
import com.app.restaurant.dto.ReceiptItemDTO;
import com.app.restaurant.model.Receipt;
import com.app.restaurant.model.ReceiptItem;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReceiptDTOToReceipt implements Converter<ReceiptDTO, Receipt> {

    @Override
    public Receipt convert(ReceiptDTO dto) {
        Receipt receipt = new Receipt();

        receipt.setId(dto.getId());
        receipt.setIssueDate(dto.getIssueDate());

        if (dto.getItemDTOs() != null) {
            for (ReceiptItemDTO riDTO : dto.getItemDTOs()) {
                ReceiptItem ri = new ReceiptItem();
                ri.setId(riDTO.getId());
                ri.setReceipt(receipt);
                ri.setQuantity(riDTO.getQuantity());
                ri.setItemStatus(riDTO.getItemStatus());
                ri.setAdditionalNote(riDTO.getAdditionalNote());
            }
        }

        return receipt;
    }
}